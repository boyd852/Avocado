// Generated by Dagger (https://google.github.io/dagger).
package app.doscervezas.avocado.repository;

import android.content.Context;
import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.dao.DefaultRatesDao;
import app.doscervezas.avocado.db.dao.RateDao;
import app.doscervezas.avocado.db.dao.SpendDao;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DataRepository_Factory implements Factory<DataRepository> {
  private final Provider<RateDao> rateDaoProvider;

  private final Provider<DefaultRatesDao> defaultRatesDaoProvider;

  private final Provider<SpendDao> spendDaoProvider;

  private final Provider<BudgetDao> budgetDaoProvider;

  private final Provider<Context> contextProvider;

  public DataRepository_Factory(
      Provider<RateDao> rateDaoProvider,
      Provider<DefaultRatesDao> defaultRatesDaoProvider,
      Provider<SpendDao> spendDaoProvider,
      Provider<BudgetDao> budgetDaoProvider,
      Provider<Context> contextProvider) {
    this.rateDaoProvider = rateDaoProvider;
    this.defaultRatesDaoProvider = defaultRatesDaoProvider;
    this.spendDaoProvider = spendDaoProvider;
    this.budgetDaoProvider = budgetDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public DataRepository get() {
    return provideInstance(
        rateDaoProvider,
        defaultRatesDaoProvider,
        spendDaoProvider,
        budgetDaoProvider,
        contextProvider);
  }

  public static DataRepository provideInstance(
      Provider<RateDao> rateDaoProvider,
      Provider<DefaultRatesDao> defaultRatesDaoProvider,
      Provider<SpendDao> spendDaoProvider,
      Provider<BudgetDao> budgetDaoProvider,
      Provider<Context> contextProvider) {
    return new DataRepository(
        rateDaoProvider.get(),
        defaultRatesDaoProvider.get(),
        spendDaoProvider.get(),
        budgetDaoProvider.get(),
        contextProvider.get());
  }

  public static DataRepository_Factory create(
      Provider<RateDao> rateDaoProvider,
      Provider<DefaultRatesDao> defaultRatesDaoProvider,
      Provider<SpendDao> spendDaoProvider,
      Provider<BudgetDao> budgetDaoProvider,
      Provider<Context> contextProvider) {
    return new DataRepository_Factory(
        rateDaoProvider,
        defaultRatesDaoProvider,
        spendDaoProvider,
        budgetDaoProvider,
        contextProvider);
  }

  public static DataRepository newDataRepository(
      RateDao rateDao,
      DefaultRatesDao defaultRatesDao,
      SpendDao spendDao,
      BudgetDao budgetDao,
      Context context) {
    return new DataRepository(rateDao, defaultRatesDao, spendDao, budgetDao, context);
  }
}
