package app.doscervezas.avocado.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.ui.adapters.CurrencySearchRecyclerAdapter;
import app.doscervezas.avocado.utils.CurrencySearchRecyclerViewClickListener;
import app.doscervezas.avocado.utils.JsonLoader;

import static android.content.ContentValues.TAG;

public class SearchCurrencyDialogFragment extends DialogFragment {

    RecyclerView mRecyclerView;
    CurrencySearchRecyclerAdapter mCurrencySearchRecyclerAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    CurrencySearchRecyclerViewClickListener mListener;
    TextView mCurrencySearchTextView;

    public SearchCurrencyDialogFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_currency_dialog, container, false);

        //Initialise views
        mRecyclerView = view.findViewById(R.id.currency_search_rv);
        mCurrencySearchTextView = view.findViewById(R.id.currency_search_et);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Show keyboard
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //Initialise lists
        HashMap<String, String> currenciesHashMap = new HashMap<>();
        ArrayList<String> currencyNamesList = new ArrayList<>();

        //Get json data
        try {
            String jsonData = JsonLoader.loadJSONFromAsset(getActivity(), "currency_symbols.json");
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String currencySymbol = iterator.next();
                JSONObject currencyJsonObject = jsonObject.getJSONObject(currencySymbol);
                String currencyName = currencyJsonObject.getString("name");
                currenciesHashMap.put(currencyName, currencySymbol);
                currencyNamesList.add(currencyName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Handle entering text in textview
        mCurrencySearchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                CharSequence searchQuery = mCurrencySearchTextView.getText();
                List<String> resultsList = updateList(searchQuery, currencyNamesList);
                mCurrencySearchRecyclerAdapter.setData(resultsList);
            }
        });


        //Handle click on RecyclerView
        mListener = currencyName -> {
            //get symbol from hashmap
            String currencySymbol = currenciesHashMap.get(currencyName);
            Intent intent = new Intent();
            intent.putExtra("CURRENCY_SYMBOL", currencySymbol);
            this.getTargetFragment().onActivityResult(getTargetRequestCode(),1, intent);
            dismiss();
        };

        mCurrencySearchRecyclerAdapter = new CurrencySearchRecyclerAdapter(currencyNamesList, mListener);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCurrencySearchRecyclerAdapter);
    }

    public List<String> updateList(CharSequence searchQuery, List<String> currencyList){
        List<String> resultsList = new ArrayList<>();
        Log.d(TAG, "updateList: searchQuery:" + searchQuery.toString());
        Log.d(TAG, "updateList: currencyList:" + currencyList.toString());
        searchQuery = searchQuery.toString().replace(" ","");
        for(String currency : currencyList){
            if (currency.toLowerCase().replace(" ", "").contains(searchQuery.toString().toLowerCase())){
                resultsList.add(currency);
            }
        }
        return resultsList;
    }
}
