package app.doscervezas.avocado.ui.fragments;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.db.entity.Budget;
import app.doscervezas.avocado.ui.AddBudgetActivity;
import app.doscervezas.avocado.ui.EditBudgetActivity;
import app.doscervezas.avocado.ui.MainActivity;
import app.doscervezas.avocado.ui.adapters.BudgetRecyclerAdapter;
import app.doscervezas.avocado.ui.viewmodels.SelectBudgetViewModel;
import app.doscervezas.avocado.utils.BudgetEditClickListener;
import app.doscervezas.avocado.utils.BudgetRecyclerViewClickListener;
import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectBudgetFragment extends DaggerFragment {

    List<Budget> mData;
    BudgetRecyclerAdapter mBudgetRecyclerAdapter;
    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    FloatingActionButton mFab;
    BudgetRecyclerViewClickListener mListener;
    BudgetEditClickListener mEditListener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    SelectBudgetViewModel mSelectBudgetViewModel;


    public SelectBudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_budget, container, false);

        //Initialise views
        mRecyclerView = view.findViewById(R.id.budget_recycler_view);
        mFab = view.findViewById(R.id.add_budget_fab);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get view model
        mSelectBudgetViewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectBudgetViewModel.class);

        //Handle click on RecyclerView
        mListener = budgetId -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);

            //save current budget for next time app is opened
            SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = defaultPreferences.edit();
            editor.putLong("BUDGET_ID", budgetId);
            editor.apply();

            startActivity(intent);
        };

        //Handle click on edit buttons
        mEditListener = budgetId -> {
            //set current budget for editing
            SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = defaultPreferences.edit();
            editor.putLong("BUDGET_ID", budgetId);
            editor.apply();

            Intent editBudgetIntent = new Intent(getActivity(), EditBudgetActivity.class);
            startActivity(editBudgetIntent);
        };


        //Get initial list of all budgets in db
        mData = mSelectBudgetViewModel.initBudgets();

        mBudgetRecyclerAdapter = new BudgetRecyclerAdapter(mData, mListener, mEditListener);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mBudgetRecyclerAdapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAddBudgetFragment();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //update view with livedata
        mSelectBudgetViewModel.getBudgets().observe(this, budgets -> mBudgetRecyclerAdapter.setData(budgets));
    }

    public void loadAddBudgetFragment(){
        Intent intent = new Intent(getActivity(), AddBudgetActivity.class);
        startActivity(intent);
    }
}
