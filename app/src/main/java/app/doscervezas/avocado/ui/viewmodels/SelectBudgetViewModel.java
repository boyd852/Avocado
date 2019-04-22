package app.doscervezas.avocado.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.entity.Budget;

public class SelectBudgetViewModel extends ViewModel {

    BudgetDao budgetDao;

    @Inject
    public SelectBudgetViewModel(BudgetDao budgetDao){
        this.budgetDao = budgetDao;
    }

    public List<Budget> initBudgets(){
        return budgetDao.initBudgets();
    }

    public LiveData<List<Budget>> getBudgets(){
        return budgetDao.getAllBudgets();
    }


}
