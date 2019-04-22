package app.doscervezas.avocado.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import javax.inject.Singleton;

import app.doscervezas.avocado.db.converter.DateConverter;
import app.doscervezas.avocado.db.dao.RateDao;
import app.doscervezas.avocado.db.entity.RateData;

@Singleton
@Database(entities = {RateData.class}, version = 3, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class RateDatabase extends RoomDatabase {

    public abstract RateDao rateDao();
}
