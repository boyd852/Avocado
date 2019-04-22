package app.doscervezas.avocado.repository;

import androidx.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.dao.DefaultRatesDao;
import app.doscervezas.avocado.db.dao.RateDao;
import app.doscervezas.avocado.db.dao.SpendDao;
import app.doscervezas.avocado.db.entity.DefaultRatesData;
import app.doscervezas.avocado.db.entity.RateData;
import app.doscervezas.avocado.db.entity.SpendEntry;
import app.doscervezas.avocado.network.RequestQueueSingleton;
import app.doscervezas.avocado.ui.AppExecutors;
import app.doscervezas.avocado.utils.AvoDateUtils;

import static android.content.ContentValues.TAG;

public class DataRepository {

    private RateDao rateDao;
    private final SpendDao spendDao;
    private final BudgetDao budgetDao;
    private final DefaultRatesDao defaultRatesDao;
    private final Context context;

    //TODO: Add as setting
    private final String HOME_CURRENCY = "AUD";

    @Inject
    public DataRepository(RateDao rateDao, DefaultRatesDao defaultRatesDao, SpendDao spendDao, BudgetDao budgetDao, Context context) {
        this.rateDao = rateDao;
        this.defaultRatesDao = defaultRatesDao;
        this.spendDao = spendDao;
        this.budgetDao = budgetDao;
        this.context = context;
    }

    public void updateSpendDb(SpendEntry spendEntry) {

        initiateDefaultRatesDb();

        //Check for currency in defaults rate
        if (!defaultRatesDao.selectAllCurrencies().contains(spendEntry.getCurrency())) {
            //TODO: handle case where currency doesn't exist
        }

        // Attempt to get rates from local cache
        RateResponse rateResponse = getRateFromCache(spendEntry.getDate(), spendEntry.getCurrency());

        //Get home currency
        String homeCurrency = budgetDao.getBudget(spendEntry.getBudgetId()).getBudgetCurrency();
        RateResponse homeRateResponse = getRateFromCache(spendEntry.getDate(), homeCurrency);

        //Handle rate response
        if (rateResponse.isError() || homeRateResponse.isError()) {
            //TODO: Handle Error
            return;
        }

        //Set spend entry synced and rates
        spendEntry.setSynced(rateResponse.isSynced()&&homeRateResponse.isSynced());
        spendEntry.setRate(rateResponse.getRate());
        spendEntry.setHomeRate(homeRateResponse.getRate());

        //Add spend entry to Dao
        AppExecutors.getInstance().diskIO().execute(() -> {
            final long spendEntryID = spendDao.addSpendEntry(spendEntry);
            spendEntry.setId(spendEntryID);
        });

        //if sync required then attempt to get data online
        //first check for internet connection
        boolean isConnected = checkConnection();

        if (!spendEntry.isSynced() && isConnected) {
            Log.d(TAG, "addSpendEntry: Getting rate from remote servers");
            getRateFromRemote(spendEntry);
        }
    }

    private void initiateDefaultRatesDb(){
        if(defaultRatesDao.selectAll().isEmpty()){
            Log.d(TAG, "getRateFromCache: Populating data...");
            defaultRatesDao.insertAll(populateData());
        }
    }

    private RateResponse getRateFromCache(Date date, String currency) {
        /*Looks for cached rate in following order:
        1. from cached rates
        2. from cached rates close to the date
        3. from default database (stored on db creation and updated weekly)

        If none of these return a result, then it is assumed the currency is not available
        */

        boolean isSynced;
        boolean isError;
        Double rate;

        Long longDate = AvoDateUtils.convertDateToLong(date);

        //Get rate from cache
        Double cachedRate = rateDao.getRate(longDate, currency);

        if (cachedRate != null) {
            Log.d(TAG, "getRateFromCache: Cached rates used");
            rate = cachedRate;
            isSynced = true;
            isError = false;

        } else {
            //if the data for date and currency doesn't exist find nearest date and provide temp data to display
            Double tempCachedRate = null;
            //Get nearest available date
            List<Date> availableDates = rateDao.getDates();
            Date nearestDate = getNearestDate(longDate, availableDates);
            if(nearestDate != null){
                long longNearestDate = AvoDateUtils.convertDateToLong(nearestDate);
                tempCachedRate = rateDao.getTempRate(longNearestDate, currency);
            }
            if (tempCachedRate != null) {
                Log.d(TAG, "getRateFromCache: tempCachedRate temp rate used");
                rate = tempCachedRate;
                isSynced = false;
                isError = false;

            } else {
                //if no data available, used default rates
                Double defaultRate = defaultRatesDao.getDefaultRate(currency);

                if (defaultRate != null) {
                    Log.d(TAG, "getRateFromCache: defaultRate used");
                    //TODO: add method for updating default rates
                    rate = defaultRate;
                    isSynced = false;
                    isError = false;

                } else {
                    //if no default rate then set to 0 and flag for error
                    rate = 0.0;
                    isSynced = false;
                    isError = true;
                }
            }
        }

        return new RateResponse(rate, isSynced, isError);
    }

    private void getRateFromRemote(SpendEntry spendEntry) {
        /*Attempts to retrieve data from:
        1. Firebase
        2. Fixer.io
        */

        //Get reference to Firebase Database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("rates");

        //get yesterdays date for the reference to date on database
        Calendar cal = Calendar.getInstance();
        cal.setTime(spendEntry.getDate());
        cal.add(Calendar.DATE, -1);

        //Format data to retrive from firebase
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateDayBefore = df.format(cal.getTime());
        Log.d(TAG, "getRateFromRemote: dataDayBefore is: " + dateDayBefore);

        //Use date to get reference to database location
        DatabaseReference myRef = mDatabase.child(dateDayBefore).child("rates");

        ValueEventListener rateDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    //Get all rates for date from snapshot
                    for (DataSnapshot rateSnapshot : dataSnapshot.getChildren()) {
                        Double rate = rateSnapshot.getValue(Double.class);
                        String currency = rateSnapshot.getKey();
                        Log.d(TAG, "onDataChange: rate variable from dataSnapshot is: " + rate);

                        //Add rates to cache
                        RateData rateData = new RateData(spendEntry.getDate(), currency, rate);
                        Log.d(TAG, "onDataChange: RateData object is date: " + spendEntry.getDate() + ", currency: " + currency + "and rate: " + rate);
                        rateDao.addRate(rateData);
                        Log.d(TAG, "onDataChange: Firebase rates added to cache");
                    }

                    //Update entry with rate for chosen time
                    Double chosenRate = rateDao.getRate(spendEntry.getDate().getTime(), spendEntry.getCurrency());
                    Double homeRate = rateDao.getRate(spendEntry.getDate().getTime(), HOME_CURRENCY);

                    if (chosenRate != null && homeRate != null) {
                        Log.d(TAG, "onDataChange: Updating spendEntry with Firebase rate");
                        spendDao.updateRate(chosenRate, homeRate, spendEntry.getId(), true);
                        Log.d(TAG, "onDataChange: Chosen rate (" + chosenRate + ") and home rate (" + homeRate + ") added to database");
                        spendEntry.setSynced(true);
                    } else {
                        AppExecutors.getInstance().networkIO().execute(() -> getRateFromFixer(spendEntry));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Request to Firebase database cancelled");
            }
        };
        myRef.addListenerForSingleValueEvent(rateDataListener);
    }

    private void getRateFromFixer(SpendEntry spendEntry){

        //format date for uri
        Calendar cal = Calendar.getInstance();
        cal.setTime(spendEntry.getDate());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(cal.getTime());

        //Build uri for fixer request
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("data.fixer.io")
                .appendPath("api")
                .appendPath(date)
                .appendQueryParameter("access_key", context.getString(R.string.FIXER_API));
        String url = builder.build().toString();

        //Get request queue
        RequestQueue queue = RequestQueueSingleton.getInstance(context).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject responseRates = response.getJSONObject("rates");
                    String dateInString = response.getString("date");

                    //Convert to date object
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;

                    try {
                        date = df.parse(dateInString);
                    } catch (ParseException e){
                        e.printStackTrace();
                    }

                    //Remove time from date object
                    final Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    Date dateWithoutTime = cal.getTime();

                    //Instantiate list of objects
                    ArrayList<RateData> listOfRateDataObjects = new ArrayList<>();

                    //Iterate through currency rates and add to clist
                    Iterator keys = responseRates.keys();

                    while (keys.hasNext()) {
                        Object key = keys.next();
                        String currency = key.toString();
                        double rate = responseRates.getDouble(currency);

                        //Instantiate RateData object
                        RateData rateData = new RateData(dateWithoutTime, currency, rate);
                        listOfRateDataObjects.add(rateData);
                    }

                    //Add list of object to cache database
                    rateDao.addRates(listOfRateDataObjects.toArray(new RateData[listOfRateDataObjects.size()]));

                    //Update spend entry
                    Double rate = rateDao.getRate(date.getTime(), spendEntry.getCurrency());
                    Double homeRate = rateDao.getRate(date.getTime(), HOME_CURRENCY);
                    Log.d(TAG, "onResponse: Updating spendEntry with Fixer rate");
                    Log.d(TAG, "onResponse: Spend entry is: " + spendEntry.getDate() + spendEntry.getCost() + spendEntry.getCurrency());
                    spendDao.updateRate(rate, homeRate, spendEntry.getId(), true);
                    spendEntry.setSynced(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NetworkError){
                    Log.d(TAG, "onErrorResponse: No network available");
                } else{
                    Log.d(TAG, "onErrorResponse: Error is: " + error.toString());
                }
            }
        });

        queue.add(jsonObjectRequest);
    }

    private boolean checkConnection(){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void  syncUnsyncedData(long budgetId){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            //TODO: Deal with lack of connection (e.g. snackbar message)
            Log.d(TAG, "syncData: No Connection");
            return;}

        List<SpendEntry> unsyncedEntries = spendDao.getUnsyncedEntries(budgetId);
        for(SpendEntry spendEntry :  unsyncedEntries){
            updateSpendDb(spendEntry);
        }
    }

    public void updateHomeCurrency(String newCurrency, long budgetId){
        List<SpendEntry> entriesToUpdate = spendDao.getSpendEntriesForBudget(budgetId);
        for(SpendEntry spendEntry : entriesToUpdate){
            RateResponse homeRateResponse = getRateFromCache(spendEntry.getDate(), newCurrency);

            //Handle rate response
            if (homeRateResponse.isError()) {
                //TODO: Handle Error
                return;
            }

            //Set spend entry synced and rates
            spendEntry.setSynced(homeRateResponse.isSynced());
            spendEntry.setHomeRate(homeRateResponse.getRate());
            spendDao.updateSpendEntry(spendEntry);
        }
    }

    LiveData<Double> getTotalSpend(long budgetId){
        long startDate = AvoDateUtils.convertDateToLong(budgetDao.getBudget(budgetId).getBudgetDate());
        return spendDao.getTotalSpend(budgetId, startDate);
    }

    LiveData<Double> getTotalDaySpend(long budgetId){
        //Remove time from date
        Date date = AvoDateUtils.getCurrentDate();
        Date dateWithoutTime = AvoDateUtils.removeTimeFromDate(date);

        //Convert to long for comparison in db
        Long longDate = dateWithoutTime.getTime();
        return spendDao.getTotalDaySpend(longDate, budgetId);
    }

    private static DefaultRatesData[] populateData(){
        return new DefaultRatesData[]{
                new DefaultRatesData("AED", 4.273568),
                new DefaultRatesData("AFN", 85.47826),
                new DefaultRatesData("ALL", 125.9018),
                new DefaultRatesData("AMD", 560.8449),
                new DefaultRatesData("ANG", 2.117831),
                new DefaultRatesData("AOA", 289.3810),
                new DefaultRatesData("ARS", 32.85547),
                new DefaultRatesData("AUD", 1.585057),
                new DefaultRatesData("AWG", 2.071213),
                new DefaultRatesData("AZN", 1.977547),
                new DefaultRatesData("BAM", 1.959159),
                new DefaultRatesData("BBD", 2.327206),
                new DefaultRatesData("BDT", 97.34701),
                new DefaultRatesData("BGN", 1.955318),
                new DefaultRatesData("BHD", 0.441583),
                new DefaultRatesData("BIF", 2037.445),
                new DefaultRatesData("BMD", 1.163603),
                new DefaultRatesData("BND", 1.576918),
                new DefaultRatesData("BOB", 7.970683),
                new DefaultRatesData("BRL", 4.553989),
                new DefaultRatesData("BSD", 1.163603),
                new DefaultRatesData("BTC", 0.000175),
                new DefaultRatesData("BTN", 79.93951),
                new DefaultRatesData("BWP", 12.08927),
                new DefaultRatesData("BYN", 2.327193),
                new DefaultRatesData("BYR", 22806.61),
                new DefaultRatesData("BZD", 2.324648),
                new DefaultRatesData("CAD", 1.535676),
                new DefaultRatesData("CDF", 1821.618),
                new DefaultRatesData("CHF", 1.157145),
                new DefaultRatesData("CLF", 0.02788),
                new DefaultRatesData("CLP", 764.9641),
                new DefaultRatesData("CNY", 7.801028),
                new DefaultRatesData("COP", 3407.378),
                new DefaultRatesData("CRC", 655.1087),
                new DefaultRatesData("CUC", 1.163603),
                new DefaultRatesData("CUP", 30.83547),
                new DefaultRatesData("CVE", 110.3095),
                new DefaultRatesData("CZK", 26.09158),
                new DefaultRatesData("DJF", 206.5607),
                new DefaultRatesData("DKK", 7.453295),
                new DefaultRatesData("DOP", 57.40053),
                new DefaultRatesData("DZD", 136.7314),
                new DefaultRatesData("EGP", 20.81685),
                new DefaultRatesData("ERN", 17.76788),
                new DefaultRatesData("ETB", 31.67326),
                new DefaultRatesData("EUR", 1),
                new DefaultRatesData("FJD", 2.428211),
                new DefaultRatesData("FKP", 0.885392),
                new DefaultRatesData("GBP", 0.886037),
                new DefaultRatesData("GEL", 2.835587),
                new DefaultRatesData("GGP", 0.886067),
                new DefaultRatesData("GHS", 5.548643),
                new DefaultRatesData("GIP", 0.885736),
                new DefaultRatesData("GMD", 54.46825),
                new DefaultRatesData("GNF", 10473.58),
                new DefaultRatesData("GTQ", 8.718291),
                new DefaultRatesData("GYD", 238.1545),
                new DefaultRatesData("HKD", 9.129631),
                new DefaultRatesData("HNL", 27.83461),
                new DefaultRatesData("HRK", 7.360371),
                new DefaultRatesData("HTG", 74.77312),
                new DefaultRatesData("HUF", 330.2188),
                new DefaultRatesData("IDR", 16814.06),
                new DefaultRatesData("ILS", 4.264583),
                new DefaultRatesData("IMP", 0.886067),
                new DefaultRatesData("INR", 80.17223),
                new DefaultRatesData("IQD", 1377.705),
                new DefaultRatesData("IRR", 49685.84),
                new DefaultRatesData("ISK", 124.7382),
                new DefaultRatesData("JEP", 0.886067),
                new DefaultRatesData("JMD", 149.8836),
                new DefaultRatesData("JOD", 0.824416),
                new DefaultRatesData("JPY", 128.9690),
                new DefaultRatesData("KES", 117.0584),
                new DefaultRatesData("KGS", 79.17991),
                new DefaultRatesData("KHR", 4730.045),
                new DefaultRatesData("KMF", 490.2026),
                new DefaultRatesData("KPW", 1047.243),
                new DefaultRatesData("KRW", 1306.842),
                new DefaultRatesData("KWD", 0.352108),
                new DefaultRatesData("KYD", 0.954352),
                new DefaultRatesData("KZT", 399.0925),
                new DefaultRatesData("LAK", 9770.772),
                new DefaultRatesData("LBP", 1751.803),
                new DefaultRatesData("LKR", 184.0819),
                new DefaultRatesData("LRD", 174.7033),
                new DefaultRatesData("LSL", 16.15084),
                new DefaultRatesData("LTL", 3.547471),
                new DefaultRatesData("LVL", 0.722074),
                new DefaultRatesData("LYD", 1.594489),
                new DefaultRatesData("MAD", 11.04887),
                new DefaultRatesData("MDL", 19.59157),
                new DefaultRatesData("MGA", 3804.981),
                new DefaultRatesData("MKD", 61.26369),
                new DefaultRatesData("MMK", 1613.916),
                new DefaultRatesData("MNT", 2860.136),
                new DefaultRatesData("MOP", 9.402961),
                new DefaultRatesData("MRO", 413.0786),
                new DefaultRatesData("MUR", 40.31884),
                new DefaultRatesData("MVR", 18.11709),
                new DefaultRatesData("MWK", 830.1491),
                new DefaultRatesData("MXN", 23.30172),
                new DefaultRatesData("MYR", 4.709059),
                new DefaultRatesData("MZN", 68.35003),
                new DefaultRatesData("NAD", 16.13683),
                new DefaultRatesData("NGN", 417.1501),
                new DefaultRatesData("NIO", 36.36262),
                new DefaultRatesData("NOK", 9.507565),
                new DefaultRatesData("NPR", 127.7403),
                new DefaultRatesData("NZD", 1.73668),
                new DefaultRatesData("OMR", 0.44740),
                new DefaultRatesData("PAB", 1.16360),
                new DefaultRatesData("PEN", 3.81976),
                new DefaultRatesData("PGK", 3.824788),
                new DefaultRatesData("PHP", 62.250311),
                new DefaultRatesData("PKR", 141.319561),
                new DefaultRatesData("PLN", 4.402723),
                new DefaultRatesData("PYG", 6566.21073),
                new DefaultRatesData("QAR", 4.235286),
                new DefaultRatesData("RON", 4.664532),
                new DefaultRatesData("RSD", 117.5695),
                new DefaultRatesData("RUB", 73.802792),
                new DefaultRatesData("RWF", 988.39915),
                new DefaultRatesData("SAR", 4.363624),
                new DefaultRatesData("SBD", 9.25402),
                new DefaultRatesData("SCR", 15.627412),
                new DefaultRatesData("SDG", 20.892724),
                new DefaultRatesData("SEK", 10.435132),
                new DefaultRatesData("SGD", 1.596824),
                new DefaultRatesData("SHP", 0.885734),
                new DefaultRatesData("SLL", 9308.822487),
                new DefaultRatesData("SOS", 663.253806),
                new DefaultRatesData("SRD", 8.622101),
                new DefaultRatesData("STD", 24510.596),
                new DefaultRatesData("SVC", 10.180985),
                new DefaultRatesData("SYP", 599.23216),
                new DefaultRatesData("SZL", 16.152204),
                new DefaultRatesData("THB", 38.715392),
                new DefaultRatesData("TJS", 10.67012),
                new DefaultRatesData("TMT", 3.967886),
                new DefaultRatesData("TND", 3.057828),
                new DefaultRatesData("TOP", 2.695026),
                new DefaultRatesData("TRY", 5.372329),
                new DefaultRatesData("TTD", 7.737376),
                new DefaultRatesData("TWD", 35.684189),
                new DefaultRatesData("TZS", 2640.214672),
                new DefaultRatesData("UAH", 30.498029),
                new DefaultRatesData("UGX", 4499.651881),
                new DefaultRatesData("USD", 1.163603),
                new DefaultRatesData("UYU", 36.571698),
                new DefaultRatesData("UZS", 9106.356137),
                new DefaultRatesData("VEF", 133479.2087),
                new DefaultRatesData("VND", 26705.84871),
                new DefaultRatesData("VUV", 126.902523),
                new DefaultRatesData("WST", 3.056808),
                new DefaultRatesData("XAF", 655.597091),
                new DefaultRatesData("XAG", 0.073634),
                new DefaultRatesData("XAU", 0.000939),
                new DefaultRatesData("XCD", 3.143105),
                new DefaultRatesData("XDR", 0.8277),
                new DefaultRatesData("XOF", 655.597091),
                new DefaultRatesData("XPF", 119.373791),
                new DefaultRatesData("YER", 290.726176),
                new DefaultRatesData("ZAR", 16.149677),
                new DefaultRatesData("ZMK", 10473.82143),
                new DefaultRatesData("ZMW", 11.612669),
                new DefaultRatesData("ZWL", 375.093205)
        };
    }

    private Date getNearestDate(long targetDate, List<Date> dateList){
        Date nearestDate = null;
        int index = 0;
        long prevDiff = -1;
        for (Date date : dateList) {
            long currDiff = Math.abs(date.getTime() - targetDate);
            if (prevDiff == -1 || currDiff < prevDiff) {
                prevDiff = currDiff;
                nearestDate = date;
            }
        }
        return nearestDate;
    }

    public List<String> getCurrencyList(){
        return defaultRatesDao.selectAllCurrencies();
    }
}
