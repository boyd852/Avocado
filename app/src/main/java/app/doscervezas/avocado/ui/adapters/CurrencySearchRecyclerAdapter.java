package app.doscervezas.avocado.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.utils.CurrencySearchRecyclerViewClickListener;

import static android.content.ContentValues.TAG;

public class CurrencySearchRecyclerAdapter extends RecyclerView.Adapter<CurrencySearchRecyclerAdapter.MyViewHolder> {

    private List<String> currencyNames;
    CurrencySearchRecyclerViewClickListener listener;

    public CurrencySearchRecyclerAdapter(List<String> currencyNamesInit, CurrencySearchRecyclerViewClickListener listener){
        this.currencyNames = new ArrayList<>(currencyNamesInit);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CurrencySearchRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_list_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencySearchRecyclerAdapter.MyViewHolder holder, int position) {
        holder.bind(currencyNames.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if (currencyNames == null) return 0;
        return currencyNames.size();
    }

    public void setData(List<String> newData){
        Log.d(TAG, "setData: updating using live data");
        if (currencyNames !=null){
            MyDiffCallback myDiffCallback = new MyDiffCallback(currencyNames, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(myDiffCallback);
            currencyNames.clear();
            currencyNames.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView currencyNameTextView;
        private View itemView;

        MyViewHolder(View view){
            super(view);
            currencyNameTextView = view.findViewById(R.id.currency_name_et);
            itemView = view;
        }

        void bind(final String currencyName, CurrencySearchRecyclerViewClickListener listener){
            if(currencyName != null) {
                currencyNameTextView.setText(currencyName);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener == null) {
                        Log.d(TAG, "onClick: listener is null");
                        return;
                    }
                    listener.onClick(currencyName);
                }
            });
        }
    }

    class MyDiffCallback extends DiffUtil.Callback{

        private final List<String> oldEntries, newEntries;

        MyDiffCallback(List<String> oldEntries, List<String> newEntries){
            this.oldEntries = oldEntries;
            this.newEntries = newEntries;
        }

        @Override
        public int getOldListSize() {
            return oldEntries.size();
        }

        @Override
        public int getNewListSize() {
            return newEntries.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldEntries.get(oldItemPosition) == newEntries.get(newItemPosition);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldEntries.get(oldItemPosition).equals(newEntries.get(newItemPosition));
        }
    }
}
