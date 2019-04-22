package app.doscervezas.avocado.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.db.entity.SpendEntry;
import app.doscervezas.avocado.utils.AvoDateUtils;
import app.doscervezas.avocado.utils.DetailRecyclerViewClickListener;

import static android.content.ContentValues.TAG;

public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailRecyclerAdapter.MyViewHolder> {

    private List<SpendEntry> entries;
    private int notSyncedTextColor;
    private int syncedTextColor;

    DetailRecyclerViewClickListener listener;

    public DetailRecyclerAdapter(List<SpendEntry> entries, int notSyncedTextColor, int syncedTextColor, DetailRecyclerViewClickListener listener){
        this.entries = entries;
        this.notSyncedTextColor = notSyncedTextColor;
        this.syncedTextColor = syncedTextColor;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        boolean removeTitle = true;
        SpendEntry currentSpendEntry = entries.get(position);
        if(position > 0 && entries.get(position-1).getDate().equals(currentSpendEntry.getDate())){
            removeTitle = true;
        } else {
            removeTitle = false;
        }
        holder.bind(currentSpendEntry, listener, removeTitle);
    }

    @Override
    public int getItemCount() {
        if (entries == null) return 0;
        return entries.size();
    }

    public void setData(List<SpendEntry> newData){
        if (entries !=null){
            MyDiffCallback myDiffCallback = new MyDiffCallback(entries, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(myDiffCallback);
            entries.clear();
            entries.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView entryDesc, entrySpend, entryCurrency, entryHomeSpend, entryHomeCurrency, entrySynced, entryCategory, entryDate;
        private SpendEntry spendEntry;
        private View itemView;

        MyViewHolder(View view){
            super(view);
            entryDesc = view.findViewById(R.id.list_desc_tv);
            entrySpend = view.findViewById(R.id.list_spend_tv);
            entryCurrency = view.findViewById(R.id.list_currency_tv);
            entryHomeSpend = view.findViewById(R.id.list_home_spend_tv);
            entryCategory = view.findViewById(R.id.list_category_tv);
            entryDate = view.findViewById(R.id.list_date);
            itemView = view;
        }

          void bind(final SpendEntry spendEntry, DetailRecyclerViewClickListener listener, boolean removeTitle){
            if(spendEntry != null){

                if(removeTitle){
                    entryDate.setVisibility(View.GONE);
                }

                entryDate.setText(AvoDateUtils.convertDateToString(spendEntry.getDate(), "EEEE, dd MMM yyyy"));
                entryDesc.setText(spendEntry.getDescription());
                entryCurrency.setText(spendEntry.getCurrency());
                entryCategory.setText(spendEntry.getCategory());
                if(!spendEntry.isSynced()){
                    entryHomeSpend.setTextColor(notSyncedTextColor);
                } else {
                    entryHomeSpend.setTextColor(syncedTextColor);
                }
                double homeSpend = (spendEntry.getHomeRate()/spendEntry.getRate())*spendEntry.getCost();
                NumberFormat formatter = new DecimalFormat("#,###.##");
                String formattedAwaySpend = formatter.format(spendEntry.getCost());
                String formattedHomeSpend = formatter.format(homeSpend);
                entrySpend.setText(formattedAwaySpend);
                entryHomeSpend.setText(formattedHomeSpend);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(spendEntry.getId());
                }
            });
        }
    }

    class MyDiffCallback extends DiffUtil.Callback{

        private final List<SpendEntry> oldEntries, newEntries;

        MyDiffCallback(List<SpendEntry> oldEntries, List<SpendEntry> newEntries){
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
            return oldEntries.get(oldItemPosition).getId() == newEntries.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldEntries.get(oldItemPosition).equals(newEntries.get(newItemPosition));
        }
    }
}
