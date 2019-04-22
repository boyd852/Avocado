package app.doscervezas.avocado.ui.fragments;


import android.animation.ObjectAnimator;
import android.app.Activity;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.ui.viewmodels.AddEntryViewModel;
import app.doscervezas.avocado.utils.AvoDateUtils;
import app.doscervezas.avocado.utils.DatePickerFragment;
import dagger.android.support.DaggerFragment;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEntryFragment extends DaggerFragment {

    private int mPage;
    private EditText mEditSpend, mEditDesc;
    private AutoCompleteTextView mEditCurrency;
    private TextView mEditDate, mSpendTotal, mDaySpendTotal, mEditDateSecondary;
    private ImageView mCurrencySearch;
    private MotionLayout mFabMotionLayout, mDateMotionLayout;
    private Boolean fabIsOpen, datesAreSplit;
    private View mAddEntryBg;
    private ToggleButton mSplitDateToggle;

    FloatingActionButton mFab, mTransportFab, mFoodFab, mAttractionFab, mAccomFab, mMiscFab;
    long mCurrentBudget;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AddEntryViewModel mAddEntryViewModel;

    public AddEntryFragment(){
        //Necessary empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_entry, container, false);

        //Set current budget
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCurrentBudget = sharedPreferences.getLong("BUDGET_ID", 0);

        //Set date in EditDates
        mEditDate = view.findViewById(R.id.date_tv);
        mEditDateSecondary = view.findViewById(R.id.date_secondary_tv);
        mEditDate.setText(AvoDateUtils.convertDateToString(AvoDateUtils.getCurrentDate(), "dd/MM/yyyy"));
        mEditDateSecondary.setText(AvoDateUtils.convertDateToString(AvoDateUtils.getTomorrowDate(), "dd/MM/yyyy"));

        //Initialise views
        mEditSpend = view.findViewById(R.id.spend_et);
        mEditDesc = view.findViewById(R.id.description_et);
        mEditCurrency = view.findViewById(R.id.currency_et);
        mSpendTotal = view.findViewById(R.id.total_tv);
        mDaySpendTotal = view.findViewById(R.id.dayTotal_tv);
        mCurrencySearch = view.findViewById(R.id.search_iv);
        mAddEntryBg = view.findViewById(R.id.add_entry_bg);
        mFabMotionLayout = view.findViewById(R.id.add_entry_fab_motion_layout);
        mDateMotionLayout = view.findViewById(R.id.add_entry_date_motion);

        mFab = view.findViewById(R.id.add_entry_fab);
        mTransportFab = view.findViewById(R.id.transport_category_fab);
        mFoodFab = view.findViewById(R.id.food_category_fab);
        mAttractionFab = view.findViewById(R.id.attraction_category_fab);
        mAccomFab = view.findViewById(R.id.accom_category_fab);
        mMiscFab = view.findViewById(R.id.misc_category_fab);

        //Initialise booleans
        fabIsOpen = false;
        datesAreSplit = false;

        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prepare DatePickerFragment
                DatePickerFragment myDatePickerFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("Date", mEditDate.getText().toString());
                myDatePickerFragment.setArguments(data);
                myDatePickerFragment.setTargetFragment(AddEntryFragment.this, 0);
                myDatePickerFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        mEditDateSecondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Prepare DatePickerFragment
                DatePickerFragment myDatePickerFragment = new DatePickerFragment();
                myDatePickerFragment.setTargetFragment(AddEntryFragment.this, 2);
                myDatePickerFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        mCurrencySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open search fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment dialogFragment = new SearchCurrencyDialogFragment();
                dialogFragment.setTargetFragment(AddEntryFragment.this, 1);
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
                if(!fabIsOpen){
                    showFabMenu();
                }else{
                    closeFabMenu();
                }
            }
        });

        mTransportFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit("Transport");
                closeFabMenu();
            }
        });

        mFoodFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit("Food");
                closeFabMenu();
            }
        });

        mAttractionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit("Attraction");
                closeFabMenu();
            }
        });

        mAccomFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit("Accommodation");
                closeFabMenu();
            }
        });

        mMiscFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit("Misc.");
                closeFabMenu();
            }
        });

        //Manage 'done' button on keyboard
        mEditDesc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    showFabMenu();
                    mEditDesc.clearFocus();
                    return false;
                }
                return true;
            }
        });

       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Show keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        //Call view model
        mAddEntryViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddEntryViewModel.class);

        //Observers for total spend and total day spend
        final Observer<Double> totalObserver = new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double number) {
                if (number == null) {
                    mSpendTotal.setText("0.00");
                } else {
                    NumberFormat formatter = new DecimalFormat("#,###.##");
                    String formattedNumber = formatter.format(number);
                    mSpendTotal.setText(String.valueOf(formattedNumber));
                }
            }
        };

        mAddEntryViewModel.getTotalSpend(mCurrentBudget).observe(this, totalObserver);
        final Observer<Double> totalDayObserver = new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double number) {
                if (number == null) {
                    mDaySpendTotal.setText("0.00");
                } else {
                    NumberFormat formatter = new DecimalFormat("#,###.##");
                    String formattedNumber = formatter.format(number);
                    mDaySpendTotal.setText(String.valueOf(formattedNumber));
                }
            }
        };
        mAddEntryViewModel.getTotalDaySpend(mCurrentBudget).observe(this, totalDayObserver);

        final List<String> CURRENCIES = mAddEntryViewModel.getCurrencyList();
        Log.d(TAG, "onActivityCreated:"+ CURRENCIES.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, CURRENCIES);
        mEditCurrency.setThreshold(1);
        mEditCurrency.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mEditSpend.requestFocus();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String currentCurrency = sharedPreferences.getString("CURRENT_CURRENCY", "");
        mEditCurrency.setText(currentCurrency);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            if(mEditSpend!=null){
                mEditSpend.requestFocus();
                //Show the keybaord
                showKeyboard();
            }
        } else {

        }
    }

    //get date from date or currency
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public void onClickSubmit(String category) {

        if (TextUtils.isEmpty(mEditSpend.getText().toString())) {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter cost", Toast.LENGTH_LONG).show();
            return;
        }

        double spend = Double.parseDouble(mEditSpend.getText().toString());
        String date = mEditDate.getText().toString();
        String desc = mEditDesc.getText().toString();
        String currency = mEditCurrency.getText().toString();

        //save current currency
        SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.putString("CURRENT_CURRENCY", currency);
        editor.apply();

        String dateSecondary = "";
        if(datesAreSplit) {
            dateSecondary = mEditDateSecondary.getText().toString();
        }

        mAddEntryViewModel.addSpendEntry(spend, date, desc, currency, mCurrentBudget, category, datesAreSplit, dateSecondary);
        mEditSpend.requestFocus();

        Toast toast = Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_LONG);
        toast.show();

        mEditDesc.setText(null);
        mEditSpend.setText(null);
        mEditDate.setText(AvoDateUtils.convertDateToString(AvoDateUtils.getCurrentDate(), "dd/MM/yyyy"));
    }

    private void showFabMenu(){

        mAddEntryBg.setVisibility(View.VISIBLE);
        mAddEntryBg.requestFocus();
        ObjectAnimator.ofFloat(mAddEntryBg, "alpha", 0, 0.7f)
                .setDuration(200)
                .start();

        mFabMotionLayout.transitionToEnd();

        hideKeyboardFrom(getActivity(), mEditDesc);

        fabIsOpen = true;
    }

    private void closeFabMenu(){

        mFabMotionLayout.transitionToStart();

        ObjectAnimator.ofFloat(mAddEntryBg, "alpha", 0.7f, 0)
                .setDuration(200)
                .start();
        mAddEntryBg.setVisibility(View.GONE);

        mEditSpend.requestFocus();
        showKeyboard();

        fabIsOpen = false;
    }

    private void splitDates(){
        mDateMotionLayout.transitionToEnd();
        datesAreSplit = true;
    }

    private void unSplitDates(){
        mDateMotionLayout.transitionToStart();
        datesAreSplit = false;
    }

    private void showKeyboard(){
        //Show the keybaord
        Log.d(TAG, "onActivityResult: showing keyboard");
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditSpend, InputMethodManager.SHOW_IMPLICIT);
    }

    private static void hideKeyboardFrom(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
