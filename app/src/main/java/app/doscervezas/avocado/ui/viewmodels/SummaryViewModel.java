package app.doscervezas.avocado.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.dao.SpendDao;
import app.doscervezas.avocado.db.entity.Budget;
import app.doscervezas.avocado.utils.AvoDateUtils;

public class SummaryViewModel extends ViewModel {

    BudgetDao budgetDao;
    SpendDao spendDao;

    @Inject
    public SummaryViewModel(BudgetDao budgetDao, SpendDao spendDao){
        this.budgetDao = budgetDao;
        this.spendDao = spendDao;
    }

    public LiveData<Double> getDayRemaining(long budgetId, Date date){
        //get required values
        long longDate = AvoDateUtils.convertDateToLong(date);
        Double dailyBudget = budgetDao.getBudget(budgetId).getDailyBudget();
        LiveData<Double> totalDaySpend = spendDao.getTotalDaySpend(longDate, budgetId);

        //tranform totalDaySpend to remaining
        return Transformations.switchMap(totalDaySpend, daySpend -> {

            if(daySpend ==null){
                daySpend = 0.0;
            }
            Double dayRemaining = dailyBudget - daySpend;

            MutableLiveData<Double> dayRemainingLiveData = new MutableLiveData<>();
            dayRemainingLiveData.setValue(dayRemaining);
            return dayRemainingLiveData;
        });
    }

    public LiveData<Double> getTotalRemaining(long budgetId){
        //get required values
        Budget budget = budgetDao.getBudget(budgetId);

        //Get values for transformation
        Double dailyBudget = budget.getDailyBudget();
        Date startDate = budget.getBudgetDate();
        long daysSoFar = TimeUnit.DAYS.convert(AvoDateUtils.getCurrentDate().getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
        LiveData<Double> totalSpend = spendDao.getTotalSpend(budgetId, AvoDateUtils.convertDateToLong(startDate));

        //transform totalSpend to remainingTotalSpend
        return Transformations.switchMap(totalSpend, spend -> {

            if(spend == null){
                spend = 0.0;
            }

            Double totalRemaining = (dailyBudget * (daysSoFar + 1) - spend);

            MutableLiveData<Double> totalRemainingLiveData = new MutableLiveData<>();
            totalRemainingLiveData.setValue(totalRemaining);
            return totalRemainingLiveData;
        });
    }

    public LiveData<Double> getTotalSpend(long budgetId){
        long startDate = AvoDateUtils.convertDateToLong(budgetDao.getBudget(budgetId).getBudgetDate());
        return spendDao.getTotalSpend(budgetId, startDate);
    }




}
