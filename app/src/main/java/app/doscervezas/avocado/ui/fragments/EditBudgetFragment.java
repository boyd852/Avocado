package app.doscervezas.avocado.ui.fragments;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import javax.inject.Inject;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.db.entity.Budget;
import app.doscervezas.avocado.ui.viewmodels.EditBudgetViewModel;
import app.doscervezas.avocado.utils.AvoDateUtils;
import app.doscervezas.avocado.utils.DatePickerFragment;
import dagger.android.support.DaggerFragment;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditBudgetFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private EditBudgetViewModel mEditBudgetViewModel;

    //Initiate references
    EditText mBudgetName, mBudgetCurrency, mDailyBudget;
    ImageView mCurrencySearch;
    TextView mDateSelect;
    FloatingActionButton mFab;
    long mCurrentBudgetId;
    Budget mCurrentBudget;
    String mCurrentCurrency;
    boolean mCurrencyChanged;


    public EditBudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_budget, container, false);

        //Initialize views
        mBudgetName = view.findViewById(R.id.edit_budget_name_et);
        mBudgetCurrency = view.findViewById(R.id.edit_budget_currency_et);
        mDailyBudget = view.findViewById(R.id.edit_budget_daily_et);
        mDateSelect = view.findViewById(R.id.edit_budget_date_select_tv);
        mFab = view.findViewById(R.id.update_budget_fab);
        mCurrencySearch = view.findViewById(R.id.edit_budget_search_iv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Initialise view model
        mEditBudgetViewModel = ViewModelProviders.of(this, viewModelFactory).get(EditBudgetViewModel.class);

        //Get current budget and update fields
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCurrentBudgetId = sharedPreferences.getLong("BUDGET_ID", 0);
        mCurrentBudget = mEditBudgetViewModel.getBudget(mCurrentBudgetId);

        mBudgetName.setText(mCurrentBudget.getBudgetName());
        mBudgetCurrency.setText(mCurrentBudget.getBudgetCurrency());
        mDailyBudget.setText(mCurrentBudget.getDailyBudget().toString());

        //Get currency
        mCurrentCurrency = mCurrentBudget.getBudgetCurrency();
        mCurrencyChanged = false;

        //Initiate date
        mDateSelect.setText(AvoDateUtils.convertDateToString(AvoDateUtils.getCurrentDate(),"dd/MM/yyyy"));

        //Handle click of date select
        mDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment myDatePickerFragment = new DatePickerFragment();
                myDatePickerFragment.setTargetFragment(EditBudgetFragment.this, 0);
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
                dialogFragment.setTargetFragment(EditBudgetFragment.this, 1);
                dialogFragment.show(ft, "dialog");
            }
        });

        //Handle fab click
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBudget();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getActivity().finish();
            }
        });
    }

    //add toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_edit_budget, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){

            case R.id.menu_delete_budget:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Are you sure?");
                builder.setMessage("This will delete the current budget permanently.");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mEditBudgetViewModel.deleteBudget(mCurrentBudgetId);
                                getActivity().finish();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;

            case R.id.menu_cancel:
                //go back to detail fragment
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public void updateBudget(){
        String updatedName = mBudgetName.getText().toString();
        String updatedCurrency = mBudgetCurrency.getText().toString();
        Double updatedDailyBudget = Double.valueOf(mDailyBudget.getText().toString());
        Date updatedDate = AvoDateUtils.convertStringToDate(mDateSelect.getText().toString(), "dd/MM/yyyy");

        //Check for change in currency
        if(!mCurrentCurrency.equals(mBudgetCurrency.getText().toString())){
            mCurrencyChanged = true;
        }

        mEditBudgetViewModel.updateBudget(updatedName, updatedCurrency, updatedDailyBudget, updatedDate, mCurrentBudgetId, mCurrencyChanged);
    }

    public void showKeyboard(){
        //Show the keybaord
        Log.d(TAG, "onActivityResult: showing keyboard");
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mDailyBudget, InputMethodManager.SHOW_IMPLICIT);
    }
}
