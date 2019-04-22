package app.doscervezas.avocado.ui.fragments;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.db.entity.DateHeader;
import app.doscervezas.avocado.db.entity.SpendEntry;
import app.doscervezas.avocado.ui.EditEntryActivity;
import app.doscervezas.avocado.ui.viewmodels.DetailFragmentViewModel;
import app.doscervezas.avocado.ui.adapters.DetailRecyclerAdapter;
import app.doscervezas.avocado.utils.AvoDateUtils;
import app.doscervezas.avocado.utils.DatePickerFragment;
import app.doscervezas.avocado.utils.DetailRecyclerViewClickListener;
import dagger.android.support.DaggerFragment;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends DaggerFragment implements SwipeRefreshLayout.OnRefreshListener {

    Fragment thisFragment;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private DetailFragmentViewModel mDetailFragmentViewModel;

    RecyclerView mRecyclerView;
    DetailRecyclerAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ImageView mRightArrow;
    ImageView mLeftArrow;
    TextView mChosenDateTextView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Boolean mIsConnected;
    DetailRecyclerViewClickListener mListener;
    long mCurrentBudget;

    List<SpendEntry> mData;
    List<Object> mDataWithHeadings;

    public static DetailFragment create(){
        return new DetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mRecyclerView = view.findViewById(R.id.detail_recycler_view);
        mRightArrow = view.findViewById(R.id.right_arrow_iv);
        mLeftArrow = view.findViewById(R.id.left_arrow_iv);
        mChosenDateTextView = view.findViewById(R.id.chosen_date_tv);
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        thisFragment = this;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Get reference to view models
        mDetailFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailFragmentViewModel.class);

        //Get current budget
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCurrentBudget = sharedPreferences.getLong("BUDGET_ID", 0);

        //click listener for recycler view
        mListener = spendEntryId -> {
            //save current tab for return
            SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = defaultPreferences.edit();
            editor.putInt("CURRENT_TAB", 1);
            editor.apply();

            Intent intent = new Intent(getActivity(), EditEntryActivity.class);
            intent.putExtra("SPEND_ENTRY_ID", spendEntryId);
            startActivity(intent);
        };


        //get default text color for adapter
        int notSyncedTextColor = getResources().getColor(R.color.colorNotSynced);
        int syncedTextColor = getResources().getColor(android.R.color.tab_indicator_text);

        //Check for connection and sync if present
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        mIsConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(mIsConnected){
            syncData();
        }

        //Initialize spend entries without live data
        mData = mDetailFragmentViewModel.initSpendEntries(mCurrentBudget);

        //Add top header
        DateHeader topDateHeader = new DateHeader(mData.get(0).getDate());
        mDataWithHeadings.add(topDateHeader);

        //Add data with headings
        for(int i=0; i<mData.size(); i++){
            if (i > 0 && mData.get(i) == mData.get(i - 1)) {
                SpendEntry currentSpendEntry = mData.get(i);
                DateHeader dateHeader = new DateHeader(currentSpendEntry.getDate());
                mDataWithHeadings.add(i, dateHeader);
            }
            mDataWithHeadings.add(mData.get(i));
        }

        mAdapter = new DetailRecyclerAdapter(mData, notSyncedTextColor, syncedTextColor, mListener);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        //Add divider lines to recycler view
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(itemDecoration);

        //change in chosenDate trigger function to get new spendEntries stored in spendEntryLiveData
        LiveData<List<SpendEntry>> spendEntryLiveData = mDetailFragmentViewModel.getSpendEntriesForBudgetLive(mCurrentBudget);

        //Observe changes in spendEntryLiveData and update (i.e. when date changes)
        spendEntryLiveData.observe(this, mAdapter::setData);

//        mRightArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: Right arrow clicked");
//                //Update chosenDate
//                chosenDate.setValue(AvoDateUtils.addDaysToDate(chosenDate.getValue(), 1));
//                String chosenDateString = AvoDateUtils.convertDateToString(chosenDate.getValue(), "dd/MM/yyyy");
//                mChosenDateTextView.setText(chosenDateString);
//            }
//        });
//
//        mLeftArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: Left arrow clicked");
//
//                //Update date to day before
//                chosenDate.setValue(AvoDateUtils.addDaysToDate(chosenDate.getValue(), -1));
//                String chosenDateString = AvoDateUtils.convertDateToString(chosenDate.getValue(), "dd/MM/yyyy");
//                mChosenDateTextView.setText(chosenDateString);
//            }
//        });

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mSwipeRefreshLayout.setRefreshing(true);
                                        syncData();
                                    }
                                }
        );
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh called from SwipeRefreshLayout");
        syncData();
    }

    public void syncData(){
        mDetailFragmentViewModel.syncData(mCurrentBudget);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 400);
    }
}
