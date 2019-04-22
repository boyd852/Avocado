package app.doscervezas.avocado.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.repository.DataRepository;
import app.doscervezas.avocado.db.dao.SpendDao;
import app.doscervezas.avocado.db.entity.SpendEntry;
import app.doscervezas.avocado.utils.AvoDateUtils;


public class AddEntryViewModel extends ViewModel {

    private SpendDao spendDao;
    private BudgetDao budgetDao;
    private DataRepository dataRepository;

    @Inject
    public AddEntryViewModel(DataRepository dataRepository, SpendDao spendDao, BudgetDao budgetDao){
        this.spendDao = spendDao;
        this.dataRepository = dataRepository;
        this.budgetDao = budgetDao;
    }

    public void addSpendEntry(double spend, String dateInString, String description, String currency, long budgetId, String category, boolean datesAreSplit, String dateSecondaryInString){


        Date date = AvoDateUtils.convertStringToDate(dateInString, "dd/MM/yyyy");
        Date dateWithoutTime = AvoDateUtils.removeTimeFromDate(date);
        long daysBetween = 0;

        if(datesAreSplit){
            Date secondaryDate = AvoDateUtils.convertStringToDate(dateSecondaryInString, "dd/MM/yyyy");
            Date dateSecondaryWithoutTime = AvoDateUtils.removeTimeFromDate(secondaryDate);
            daysBetween = AvoDateUtils.getDaysBetweenDates(dateWithoutTime, dateSecondaryWithoutTime) + 1;

            double spendPerDay = spend/daysBetween;

            long lastSpendEntryGroupId = spendDao.getLastSpendEntryGroupId();
            long currentSpendEntryGroupId = lastSpendEntryGroupId + 1;

            for(int i=0; i<daysBetween; i++){
                Date spendEntryDate = AvoDateUtils.addDaysToDate(dateWithoutTime,i);

                //Declare SpendEntry
                SpendEntry spendEntry = new SpendEntry(spendPerDay, spendEntryDate, description, currency, budgetId, category, currentSpendEntryGroupId, datesAreSplit, dateWithoutTime, dateSecondaryWithoutTime, spend);

                Log.d("TAG", "addSpendEntry: currentSpendEntry = " + currentSpendEntryGroupId);

                //Pass to repository for processing
                dataRepository.updateSpendDb(spendEntry);
            }
        } else {

            long spendEntryGroupId = 0;

            //Declare SpendEntry
            SpendEntry spendEntry = new SpendEntry(spend, dateWithoutTime, description, currency, budgetId, category, spendEntryGroupId, datesAreSplit);

            //Pass to repository for processing
            dataRepository.updateSpendDb(spendEntry);
        }
    }

    public LiveData<Double> getTotalSpend(long budgetId){
        long startDate = AvoDateUtils.convertDateToLong(budgetDao.getBudget(budgetId).getBudgetDate());
        return spendDao.getTotalSpend(budgetId, startDate);
    }

    public LiveData<Double> getTotalDaySpend(long budgetId){
        Date date = AvoDateUtils.removeTimeFromDate(AvoDateUtils.getCurrentDate());
        long longDate = AvoDateUtils.convertDateToLong(date);
        return spendDao.getTotalDaySpend(longDate, budgetId);
    }

    public List<String> getCurrencyList(){
        return dataRepository.getCurrencyList();
    }
}
