package app.doscervezas.avocado.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import java.util.Date;

import javax.inject.Inject;

import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.entity.Budget;
import app.doscervezas.avocado.repository.DataRepository;

public class EditBudgetViewModel extends ViewModel {

    BudgetDao budgetDao;
    DataRepository dataRepository;

    @Inject
    public EditBudgetViewModel(BudgetDao budgetDao, DataRepository dataRepository){
        this.budgetDao = budgetDao;
        this.dataRepository = dataRepository;
    }

    public void updateBudget(String updatedName, String budgetCurrency, Double dailyBudget, Date budgetDate, long budgetId, boolean currencyChanged){
        //New budget object
        Budget updatedBudget = new Budget(updatedName, budgetCurrency, dailyBudget, budgetDate, budgetId);
        budgetDao.updateBudget(updatedBudget);

        if(currencyChanged){
            dataRepository.updateHomeCurrency(budgetCurrency, budgetId);
        }
    }

    public Budget getBudget(long budgetId){
        return budgetDao.getBudget(budgetId);
    }

    public void deleteBudget(long budgetId){budgetDao.deleteByBudgetId(budgetId);}
}
