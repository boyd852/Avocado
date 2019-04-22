package app.doscervezas.avocado.ui.fragments;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.ui.SelectBudgetActivity;
import app.doscervezas.avocado.ui.viewmodels.AddBudgetViewModel;
import app.doscervezas.avocado.utils.AvoDateUtils;
import app.doscervezas.avocado.utils.DatePickerFragment;
import app.doscervezas.avocado.utils.JsonLoader;
import dagger.android.support.DaggerFragment;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBudgetFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AddBudgetViewModel mAddBudgetViewModel;

    //Initiate views
    EditText mBudgetName, mBudgetCurrency, mDailyBudget;
    ImageView mCurrencySearch;
    TextView mDateSelect;
    FloatingActionButton mFab;

    public AddBudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_budget, container, false);

        //Initialize views
        mBudgetName = view.findViewById(R.id.add_budget_name_et);
        mBudgetCurrency = view.findViewById(R.id.add_budget_currency_et);
        mDailyBudget = view.findViewById(R.id.add_budget_daily_et);
        mDateSelect = view.findViewById(R.id.add_budget_date_select_tv);
        mFab = view.findViewById(R.id.save_new_budget_fab);
        mCurrencySearch = view.findViewById(R.id.add_budget_search_iv);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Initialise view model
        mAddBudgetViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddBudgetViewModel.class);

        //Initiate date
        mDateSelect.setText(AvoDateUtils.convertDateToString(AvoDateUtils.getCurrentDate(),"dd/MM/yyyy"));

        //Handle click of date select
        mDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment myDatePickerFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("Date", mDateSelect.getText().toString());
                myDatePickerFragment.setArguments(data);
                myDatePickerFragment.setTargetFragment(AddBudgetFragment.this, 1);
                if(getFragmentManager() !=null){
                    myDatePickerFragment.show(getFragmentManager(), "DatePicker");
                }
            }
        });

        mCurrencySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open search fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment dialogFragment = new SearchCurrencyDialogFragment();
                dialogFragment.setTargetFragment(AddBudgetFragment.this, 1);
                dialogFragment.show(ft, "dialog");
            }
        });

        //Handle fab click
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mBudgetName.getText().toString().trim() == ""){
                    Toast.makeText(getActivity(),"Insert budget name", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean nameIsUnique = mAddBudgetViewModel.checkBudgetNameUnique(mBudgetName.getText().toString());
                if(!nameIsUnique){
                    Toast.makeText(getActivity(), "Please choose unique name", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!currencyIsValid(mBudgetCurrency.getText().toString())){
                    Toast.makeText(getActivity(), "Please choose valid currency", Toast.LENGTH_LONG).show();
                    return;
                }

                int dailyBudget = 0;

                try{
                    dailyBudget = Integer.parseInt(mDailyBudget.getText().toString());
                } catch(NumberFormatException e){
                    e.printStackTrace();
                }

                if(dailyBudget < 1){
                    Toast.makeText(getActivity(), "Please include valid budget", Toast.LENGTH_LONG).show();
                    return;
                }

                addBudget();
                Snackbar.make(view, "Budget Added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

    //get date from date picker and fill edittext
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 0:
                String date = data.getStringExtra("key_date");
                mDateSelect.setText(date);
                break;

            case 1:
                String currencySymbol = data.getStringExtra("CURRENCY_SYMBOL");
                mBudgetCurrency.setText(currencySymbol);
                mDailyBudget.requestFocus();
                showKeyboard();
                break;
        }
    }

    public void addBudget(){
        String budgetName = mBudgetName.getText().toString();
        String budgetCurrency = mBudgetCurrency.getText().toString().toUpperCase();
        Double dailyBudget = Double.valueOf(mDailyBudget.getText().toString());
        Date budgetDate = AvoDateUtils.convertStringToDate(mDateSelect.getText().toString(), "dd/MM/yyyy");
        mAddBudgetViewModel.addBudget(budgetName, budgetCurrency, dailyBudget, budgetDate);
        Intent intent = new Intent(getActivity(), SelectBudgetActivity.class);
        startActivity(intent);
    }

    public void showKeyboard(){
        //Show the keybaord
        Log.d(TAG, "onActivityResult: showing keyboard");
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mDailyBudget, InputMethodManager.SHOW_IMPLICIT);
    }

    public boolean currencyIsValid(String currencySymbol){
        boolean isValid = false;
        ArrayList<String> currencySymbols = getCurrencies();
        Log.d(TAG, "checkCurrencyIsValid: currency symbol is:" + currencySymbol);
        Log.d(TAG, "checkCurrencyIsValid: " + currencySymbols);

        for(String symbol : currencySymbols){
            if(currencySymbol.equalsIgnoreCase(symbol)){
                Log.d(TAG, "currencyIsValid: symbol is" + symbol);
                isValid = true;
            }
        }
        Log.d(TAG, "currencyIsValid: " + isValid);
        return isValid;
    }

    public ArrayList<String> getCurrencies(){
        //Initialise lists
        ArrayList<String> currencySymbols = new ArrayList<>();

        //Get json data
        try {
            String jsonData = JsonLoader.loadJSONFromAsset(getActivity(), "currency_symbols.json");
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String currencySymbol = iterator.next();
                currencySymbols.add(currencySymbol);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return currencySymbols;
    }
}
