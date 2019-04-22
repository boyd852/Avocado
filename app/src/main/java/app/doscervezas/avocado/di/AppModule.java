package app.doscervezas.avocado.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import app.doscervezas.avocado.db.DefaultRatesDatabase;
import app.doscervezas.avocado.db.RateDatabase;
import app.doscervezas.avocado.db.SpendDatabase;
import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.dao.DefaultRatesDao;
import app.doscervezas.avocado.db.dao.RateDao;
import app.doscervezas.avocado.db.dao.SpendDao;
import app.doscervezas.avocado.repository.DataRepository;
import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
class AppModule {

    @Singleton
    @Provides
    SpendDatabase provideSpendDatabase(Application app){
        return Room.databaseBuilder(app, SpendDatabase.class, "Spend.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    SpendDao provideSpendDao(SpendDatabase spendDatabase){
        return spendDatabase.spendDao();
    }


    @Singleton
    @Provides
    RateDatabase provideRateDatabase(Application app){
        return Room.databaseBuilder(app, RateDatabase.class, "Rates.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    RateDao provideRateDao(RateDatabase rateDatabase){
        return rateDatabase.rateDao();
    }

    @Singleton
    @Provides
    DefaultRatesDatabase provideDefaultRatesDatabase(Application app) {
        return Room.databaseBuilder(app, DefaultRatesDatabase.class, "DefaultRates.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    DefaultRatesDao provideDefaultRatesDao(DefaultRatesDatabase defaultRatesDatabase){
        return defaultRatesDatabase.defaultRatesDao();
    }

    @Singleton
    @Provides
    BudgetDao provideBudgetDao(SpendDatabase spendDatabase){
        return spendDatabase.budgetDao();
    }

    @Singleton
    @Provides
    DataRepository provideDataRepository(RateDao rateDao, DefaultRatesDao defaultRatesDao, SpendDao spendDao, BudgetDao budgetDao, Application app){
        return new DataRepository(rateDao, defaultRatesDao, spendDao, budgetDao, app);
    }
}
