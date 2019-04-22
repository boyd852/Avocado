package app.doscervezas.avocado.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.db.entity.Budget;
import app.doscervezas.avocado.utils.BudgetEditClickListener;
import app.doscervezas.avocado.utils.BudgetRecyclerViewClickListener;

import static android.content.ContentValues.TAG;

public class BudgetRecyclerAdapter extends RecyclerView.Adapter<BudgetRecyclerAdapter.MyViewHolder> {

    List<Budget> budgets;
    BudgetRecyclerViewClickListener listener;
    BudgetEditClickListener editListener;

    public BudgetRecyclerAdapter(List<Budget> budgets, BudgetRecyclerViewClickListener listener, BudgetEditClickListener editListener){
        this.budgets = budgets;
        this.listener = listener;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public BudgetRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_list_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetRecyclerAdapter.MyViewHolder holder, int position) {
        holder.bind(budgets.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if (budgets == null) return 0;
        return budgets.size();
    }

    public void setData(List<Budget> newData){
        Log.d(TAG, "setData: updating using live data");
        if (budgets !=null){
            MyDiffCallback myDiffCallback = new MyDiffCallback(budgets, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(myDiffCallback);

            budgets.clear();
            budgets.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView budgetName, budgetCurrency, dailyBudget;
        private Budget budget;
        private ImageView budgetEdit;
        private View itemView;

        MyViewHolder(View view){
            super(view);
            budgetName = view.findViewById(R.id.budget_list_name_tv);
            budgetCurrency = view.findViewById(R.id.budget_list_currency_tv);
            dailyBudget = view.findViewById(R.id.budget_list_daily_tv);
            budgetEdit = view.findViewById(R.id.budget_list_edit_iv);
            itemView = view;
        }

        void bind(final Budget budget, BudgetRecyclerViewClickListener listener){
            if(budget != null){
                budgetName.setText(budget.getBudgetName());
                budgetCurrency.setText(budget.getBudgetCurrency());
                NumberFormat formatter = new DecimalFormat("#,###.##");
                String formattedDailyBudget = formatter.format(budget.getDailyBudget());
                dailyBudget.setText(formattedDailyBudget);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener == null) {
                        Log.d(TAG, "onClick: listener is null");
                        return;
                    }
                    listener.onClick(budget.getId());
                }
            });

            budgetEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editListener == null){
                        Log.d(TAG, "onClick: listener is null");
                        return;
                    }
                    editListener.onClick(budget.getId());
                }
            });
        }
    }

    class MyDiffCallback extends DiffUtil.Callback{

        private final List<Budget> oldEntries, newEntries;

        MyDiffCallback(List<Budget> oldEntries, List<Budget> newEntries){
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
