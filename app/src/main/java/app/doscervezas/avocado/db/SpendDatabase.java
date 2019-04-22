package app.doscervezas.avocado.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import javax.inject.Singleton;

import app.doscervezas.avocado.db.converter.DateConverter;
import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.dao.SpendDao;
import app.doscervezas.avocado.db.entity.Budget;
import app.doscervezas.avocado.db.entity.SpendEntry;

@Singleton
@Database(entities = {SpendEntry.class, Budget.class},version=12, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SpendDatabase extends RoomDatabase{

    public abstract SpendDao spendDao();
    public abstract BudgetDao budgetDao();
}


