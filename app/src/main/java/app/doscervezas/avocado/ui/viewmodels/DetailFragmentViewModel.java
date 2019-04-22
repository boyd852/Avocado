package app.doscervezas.avocado.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.dao.SpendDao;
import app.doscervezas.avocado.db.entity.SpendEntry;
import app.doscervezas.avocado.repository.DataRepository;

public class DetailFragmentViewModel extends ViewModel {

    public SpendDao spendDao;
    public BudgetDao budgetDao;
    private DataRepository dataRepository;

    @Inject
    public DetailFragmentViewModel(DataRepository dataRepository, SpendDao spendDao, BudgetDao budgetDao){
        this.spendDao = spendDao;
        this.budgetDao = budgetDao;
        this.dataRepository = dataRepository;
    }

    public LiveData<List<SpendEntry>> getSpendEntriesForBudgetLive(long budgetId){
        return spendDao.getSpendEntriesForBudgetLive(budgetId);
    }

    public List<SpendEntry> initSpendEntries(long budgetId){
        return spendDao.initSpendEntries(budgetId);
    }

    public void syncData(long budgetId){
        dataRepository.syncUnsyncedData(budgetId);
    }

}
