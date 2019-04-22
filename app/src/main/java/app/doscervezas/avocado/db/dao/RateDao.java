package app.doscervezas.avocado.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import app.doscervezas.avocado.db.entity.RateData;

@Singleton
@Dao
public interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRates(RateData... rateData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRate(RateData rateData);

    @Query("SELECT rate FROM RateData WHERE (date = :date AND currency = :currency)")
    Double getRate(Long date, String currency);

    //Todo: Order by using  ORDER BY DATEDIFF(:date, date) LIMIT 1"
    @Query("SELECT rate FROM RateData WHERE (date = :date AND currency = :currency)")
    Double getTempRate(Long date, String currency);

    @Query("SELECT date FROM RateData")
    List<Date> getDates();

}
