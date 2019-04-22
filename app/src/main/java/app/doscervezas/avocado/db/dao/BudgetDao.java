package app.doscervezas.avocado.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import javax.inject.Singleton;

import app.doscervezas.avocado.db.entity.Budget;

@Singleton
@Dao
public interface BudgetDao {

    @Insert
    void insertBudget(Budget budget);

    @Query("DELETE FROM Budget WHERE budgetName = :budgetName")
    void deleteByBudgetName(String budgetName);

    @Query("SELECT * FROM Budget")
    List<Budget> initBudgets();

    @Query("SELECT * FROM Budget")
    LiveData<List<Budget>> getAllBudgets();

    @Update
    void updateBudget(Budget updatedBudget);

    @Query("SELECT * FROM Budget WHERE id = :id")
    Budget getBudget(long id);

    @Query("SELECT id FROM Budget WHERE budgetName = :budgetName")
    long getIdFromBudgetName(String budgetName);

    @Query("SELECT budgetName FROM Budget")
    List<String> getBudgetNames();

    @Query("DELETE FROM Budget WHERE id = :id")
    void deleteByBudgetId(long id);

}
