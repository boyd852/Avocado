package app.doscervezas.avocado.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.entity.Budget;

public class AddBudgetViewModel extends ViewModel {

    BudgetDao budgetDao;

    @Inject
    public AddBudgetViewModel(BudgetDao budgetDao){
        this.budgetDao = budgetDao;
    }

    public void addBudget(String name, String currency, Double dailyBudget, Date budgetDate){
        Budget newBudget = new Budget(name, currency, dailyBudget, budgetDate);
        budgetDao.insertBudget(newBudget);
    }

    public boolean checkBudgetNameUnique(String budgetName){
        boolean isUnique = true;
        List<String> budgetNames = budgetDao.getBudgetNames();
        for(String name : budgetNames){
            if(budgetName.equalsIgnoreCase(name)){
                isUnique = false;
            }
        }
        return isUnique;
    }
}
