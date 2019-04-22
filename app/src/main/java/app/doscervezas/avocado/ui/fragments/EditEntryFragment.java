package app.doscervezas.avocado.ui.fragments;


import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Date;

import javax.inject.Inject;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.db.entity.SpendEntry;
import app.doscervezas.avocado.ui.viewmodels.EditEntryViewModel;
import app.doscervezas.avocado.utils.AvoDateUtils;
import app.doscervezas.avocado.utils.DatePickerFragment;
import dagger.android.support.DaggerFragment;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEntryFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private EditEntryViewModel mEditEntryViewModel;

    public EditEntryFragment() {
        // Required empty public constructor
    }

    long spendEntryId;
    SpendEntry selectedSpendEntry;
    EditText mEditSpend, mEditDesc, mEditCurrency;
    TextView mEditDate, mEditDateSecondary;
    FloatingActionButton mFab;
    Boolean datesAreSplit;
    ImageView mCurrencySearch;
    MotionLayout mDateMotionLayout;
    ToggleButton mSplitDateToggle;
    RadioButton mTransportRB, mFoodRB, mAttractionRB, mAccomRB, mMiscRB;
    RadioGroup mCategoryRG;
    long mCurrentBudgetId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_entry, container, false);

        //Initialise views
        mEditSpend = view.findViewById(R.id.edit_entry_spend_et);
        mEditDesc = view.findViewById(R.id.edit_entry_description_et);
        mEditCurrency = view.findViewById(R.id.edit_entry_currency_et);
        mEditDate = view.findViewById(R.id.date_tv);
        mFab = view.findViewById(R.id.edit_entry_fab);
        mCurrencySearch = view.findViewById(R.id.edit_entry_search_iv);
        mEditDateSecondary = view.findViewById(R.id.date_secondary_tv);
        mDateMotionLayout = view.findViewById(R.id.add_entry_date_motion);
        mTransportRB = view.findViewById(R.id.edit_entry_transport_rb);
        mFoodRB = view.findViewById(R.id.edit_entry_food_rb);
        mAttractionRB = view.findViewById(R.id.edit_entry_attraction_rb);
        mAccomRB = view.findViewById(R.id.edit_entry_accom_rb);
        mMiscRB = view.findViewById(R.id.edit_entry_misc_rb);
        mCategoryRG = view.findViewById(R.id.edit_entry_category_rg);

        //Get current budget and update fields
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCurrentBudgetId = sharedPreferences.getLong("BUDGET_ID", 0);


        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prepare DatePickerFragment
                Bundle data = new Bundle();
                String dateForPicker = mEditDate.getText().toString();
                data.putString("Date", dateForPicker);
                DatePickerFragment myDatePickerFragment = new DatePickerFragment();
                myDatePickerFragment.setArguments(data);
                myDatePickerFragment.setTargetFragment(EditEntryFragment.this, 0);
                myDatePickerFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        mEditDateSecondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prepare DatePickerFragment
                Bundle data = new Bundle();
                String dateForPicker = mEditDateSecondary.getText().toString();
                data.putString("Date", dateForPicker);
                DatePickerFragment myDatePickerFragment = new DatePickerFragment();
                myDatePickerFragment.setArguments(data);
                myDatePickerFragment.setTargetFragment(EditEntryFragment.this, 2);
                myDatePickerFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        mCurrencySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open search fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment dialogFragment = new SearchCurrencyDialogFragment();
                dialogFragment.setTargetFragment(EditEntryFragment.this, 1);
                dialogFragment.show(ft, "dialog");
            }
        });

        mSplitDateToggle = view.findViewById(R.id.date_split_tb);
        mSplitDateToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    splitDates();
                }
                else{
                    unSplitDates();
                }
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSpendEntry();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Initialise view model
        mEditEntryViewModel = ViewModelProviders.of(this, viewModelFactory).get(EditEntryViewModel.class);

        //Get selected spend entry
        spendEntryId = getActivity().getIntent().getLongExtra("SPEND_ENTRY_ID", 0);
        selectedSpendEntry = mEditEntryViewModel.getSpendEntry(spendEntryId);

        //Set text
        mEditDesc.setText(selectedSpendEntry.getDescription());
        mEditCurrency.setText(selectedSpendEntry.getCurrency());

        if(selectedSpendEntry.datesAreSplit()){
            mEditSpend.setText(String.valueOf(selectedSpendEntry.getTotalSpend()));
            mEditDate.setText(AvoDateUtils.convertDateToString(selectedSpendEntry.getStartDate(), "dd/MM/yyyy"));
            mEditDateSecondary.setText(AvoDateUtils.convertDateToString(selectedSpendEntry.getEndDate(), "dd/MM/yyyy"));
            mSplitDateToggle.setChecked(true);
        } else {
            mEditDate.setText(AvoDateUtils.convertDateToString(selectedSpendEntry.getDate(), "dd/MM/yyyy"));
            mEditSpend.setText(String.valueOf(selectedSpendEntry.getCost()));
        }

        String category = selectedSpendEntry.getCategory();
        switch(category){

            case "Transport":   mTransportRB.setChecked(true);
                                break;

            case "Food":        mFoodRB.setChecked(true);
                                break;

            case "Attraction":  mAttractionRB.setChecked(true);
                                break;

            case "Accommodation":       mAccomRB.setChecked(true);
                                break;

            case "Misc":        mMiscRB.setChecked(true);
                                break;
        }

        mEditSpend.requestFocus();
        showKeyboard();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_edit_entry, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){

            case R.id.menu_delete_entry:
                mEditEntryViewModel.deleteEntry(selectedSpendEntry);
                //go back to detail fragment
                getActivity().finish();
                break;

            case R.id.menu_cancel:
                //go back to detail fragment
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode) {
            case 0:
                String date = data.getStringExtra("key_date");
                mEditDate.setText(date);
                break;

            case 1:
                String currencySymbol = data.getStringExtra("CURRENCY_SYMBOL");
                mEditCurrency.setText(currencySymbol);
                mEditSpend.requestFocus();
                //Show the keybaord
                showKeyboard();
                break;

            case 2:
                String dateSecondary = data.getStringExtra("key_date");
                mEditDateSecondary.setText(dateSecondary);
                break;
        }
    }

    private void saveSpendEntry(){

        String date = mEditDate.getText().toString();
        String dateSecondary = "";

        if(datesAreSplit) {
            dateSecondary = mEditDateSecondary.getText().toString();
            Date dateSecondaryValue = AvoDateUtils.convertStringToDate(dateSecondary, "dd/MM/yyyy");
            Date dateValue = AvoDateUtils.convertStringToDate(date, "dd/MM/yyyy");
            if(!dateSecondaryValue.after(dateValue)){
                Toast.makeText(getActivity(), "End date must be after start date.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if (TextUtils.isEmpty(mEditSpend.getText().toString())) {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter cost", Toast.LENGTH_LONG).show();
            return;
        }

        double spend = Double.parseDouble(mEditSpend.getText().toString());
        String desc = mEditDesc.getText().toString();
        String currency = mEditCurrency.getText().toString();

        //Get selected category
        String category = "Error";
        int categoryId = mCategoryRG.getCheckedRadioButtonId();
        switch(categoryId){

            case R.id.edit_entry_transport_rb:  category = "Transport";
                                                break;

            case R.id.edit_entry_food_rb:       category = "Food";
                                                break;

            case R.id.edit_entry_attraction_rb: category = "Attraction";
                                                break;

            case R.id.edit_entry_accom_rb:      category = "Accommodation";
                                                break;

            case R.id.edit_entry_misc_rb:       category = "Misc";
                                                break;
        }

        //Delete old spendEntry first
        mEditEntryViewModel.deleteOldSpendEntry(spendEntryId);

        //Replace with new spendEntry
        mEditEntryViewModel.updateSpendEntry(spend, date, desc, currency, mCurrentBudgetId, category, datesAreSplit, dateSecondary);
        mEditSpend.requestFocus();

        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();

        //go back to detail fragment
        hideKeyboardFrom(getActivity(),mEditDesc);
        getActivity().finish();
    }

    private void splitDates(){
        if(mEditDateSecondary.getText() == ""){
            mEditDateSecondary.setText(AvoDateUtils.convertDateToString(AvoDateUtils.getTomorrowDate(), "dd/MM/yyyy"));
        }
        mDateMotionLayout.transitionToEnd();
        datesAreSplit = true;
    }

    private void unSplitDates(){
        mDateMotionLayout.transitionToStart();
        datesAreSplit = false;
    }

    private void showKeyboard(){
        //Show the keybaord
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditSpend, InputMethodManager.SHOW_IMPLICIT);
    }
    private static void hideKeyboardFrom(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
