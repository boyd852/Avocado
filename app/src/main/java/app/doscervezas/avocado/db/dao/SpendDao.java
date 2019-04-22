package app.doscervezas.avocado.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import javax.inject.Singleton;

import app.doscervezas.avocado.db.entity.SpendEntry;

@Singleton
@Dao
public interface SpendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addSpendEntry(SpendEntry spendEntry);

    @Delete
    void deleteEntry(SpendEntry spendEntry);

    @Query("SELECT * FROM SpendEntry WHERE budgetId= :budgetId ORDER BY date")
    List<SpendEntry> initSpendEntries(long budgetId);

    @Query("SELECT SUM((homeRate/rate)*cost) FROM SpendEntry WHERE budgetId= :budgetId AND date >= :date")
    LiveData<Double> getTotalSpend(long budgetId, long date);

    @Query("SELECT SUM((homeRate/rate)*cost) FROM SpendEntry WHERE date = :date AND budgetId= :budgetId")
    LiveData<Double> getTotalDaySpend(Long date, long budgetId);

    @Query("UPDATE SpendEntry SET rate = :rate, homeRate = :homeRate, isSynced = :isSynced WHERE id = :id")
    void updateRate(Double rate, Double homeRate, long id, Boolean isSynced);

    @Query("SELECT * FROM SpendEntry WHERE date = :date AND budgetId= :budgetId")
    LiveData<List<SpendEntry>> getSpendEntriesForDate(long date, long budgetId);

    @Query("SELECT * FROM SpendEntry WHERE isSynced = 0 AND budgetId= :budgetId")
    List<SpendEntry> getUnsyncedEntries(long budgetId);

    @Query("SELECT * FROM SpendEntry WHERE id = :id")
    SpendEntry getSpendEntryFromId(long id);

    @Query("SELECT * FROM SpendEntry WHERE budgetId = :budgetId")
    List<SpendEntry> getSpendEntriesForBudget(long budgetId);

    @Query("SELECT * FROM SpendEntry WHERE budgetId = :budgetId ORDER BY date")
    LiveData<List<SpendEntry>> getSpendEntriesForBudgetLive(long budgetId);

    @Query("SELECT spendEntryGroupId FROM SpendEntry ORDER BY spendEntryGroupId DESC LIMIT 1;")
    long getLastSpendEntryGroupId();

    @Update
    void updateSpendEntry(SpendEntry spendEntry);

    @Query("DELETE FROM SpendEntry WHERE spendEntryGroupId = :spendEntryGroupId")
    void deleteSpendEntryGroup(long spendEntryGroupId);
}
