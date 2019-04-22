package app.doscervezas.avocado.ui.fragments;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.ui.viewmodels.SummaryViewModel;
import app.doscervezas.avocado.utils.AvoDateUtils;
import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends DaggerFragment {

    TextView mDayRemaining, mYesterdayRemaining, mTotalRemaining, mTotalSpend;
    long mCurrentBudgetId;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SummaryViewModel mSummaryViewModel;

    public SummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        //Initiate views
        mDayRemaining = view.findViewById(R.id.summary_day_remaining_tv);
        mTotalRemaining = view.findViewById(R.id.summary_total_remaining_tv);
        mTotalSpend = view.findViewById(R.id.summary_total_spend_tv);
        mYesterdayRemaining = view.findViewById(R.id.summary_yesterday_remaining_tv);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Initialise view model
        mSummaryViewModel = ViewModelProviders.of(this, viewModelFactory).get(SummaryViewModel.class);

        //Get current budget
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCurrentBudgetId = sharedPreferences.getLong("BUDGET_ID", 0);

        //get current date
        Date currentDate = AvoDateUtils.removeTimeFromDate(AvoDateUtils.getCurrentDate());

        //get yesterday's date
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterdaysDate = AvoDateUtils.removeTimeFromDate(cal.getTime());

        //Get today remaining
        mSummaryViewModel.getDayRemaining(mCurrentBudgetId, currentDate).observe(this, remaining -> {

            //Format day remaining result and set text
            NumberFormat formatter = new DecimalFormat("#,###.##");
            String formattedDayRemaining = formatter.format(remaining);
            mDayRemaining.setText(formattedDayRemaining);

        });

        //Get total remaining
        mSummaryViewModel.getTotalRemaining(mCurrentBudgetId).observe(this, remaining -> {

            //Format remaining result and set text
            NumberFormat formatter = new DecimalFormat("#,###.##");
            String formattedTotalRemaining = formatter.format(remaining);
            mTotalRemaining.setText(formattedTotalRemaining);

        });

        //Get yesterday remaining
        mSummaryViewModel.getDayRemaining(mCurrentBudgetId, yesterdaysDate).observe(this, remaining -> {
            //Format remaining result and set text
            NumberFormat formatter = new DecimalFormat("#,###.##");
            String formattedTotalRemaining = formatter.format(remaining);
            mYesterdayRemaining.setText(formattedTotalRemaining);
        });

        //Get total spend
        mSummaryViewModel.getTotalSpend(mCurrentBudgetId).observe(this, totalSpend ->{

            if(totalSpend == null){
                totalSpend = 0.0;
            }
            //Format remaining result and set text
            NumberFormat formatter = new DecimalFormat("#,###.##");
            String formattedTotalSpend = formatter.format(totalSpend);
            mTotalSpend.setText(formattedTotalSpend);
        });
    }
}
