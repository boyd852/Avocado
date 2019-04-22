package app.doscervezas.avocado.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import javax.inject.Singleton;

import app.doscervezas.avocado.db.entity.DefaultRatesData;

@Singleton
@Dao
public interface DefaultRatesDao {

    @Query("DELETE FROM DefaultRatesData")
    void clearDatabase();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDefaultRates(DefaultRatesData... defaultRatesDatas);

    @Query("SELECT rate FROM DefaultRatesData WHERE (currency = :currency)")
    Double getDefaultRate(String currency);

    @Insert
    void insertAll(DefaultRatesData... defaultRatesDatas);

    @Query("SELECT * FROM DefaultRatesData")
    List<DefaultRatesData> selectAll();

    @Query("SELECT currency FROM DefaultRatesData")
    List<String> selectAllCurrencies();
}
