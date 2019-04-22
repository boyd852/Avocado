package app.doscervezas.avocado.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import javax.inject.Singleton;

import app.doscervezas.avocado.db.converter.DateConverter;
import app.doscervezas.avocado.db.dao.DefaultRatesDao;
import app.doscervezas.avocado.db.entity.DefaultRatesData;

@Singleton
@Database(entities = {DefaultRatesData.class}, version = 3, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class DefaultRatesDatabase extends RoomDatabase {

    public abstract DefaultRatesDao defaultRatesDao();

}
