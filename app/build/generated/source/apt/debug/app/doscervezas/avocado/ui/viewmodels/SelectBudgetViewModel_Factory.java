// Generated by Dagger (https://google.github.io/dagger).
package app.doscervezas.avocado.ui.viewmodels;

import app.doscervezas.avocado.db.dao.BudgetDao;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SelectBudgetViewModel_Factory implements Factory<SelectBudgetViewModel> {
  private final Provider<BudgetDao> budgetDaoProvider;

  public SelectBudgetViewModel_Factory(Provider<BudgetDao> budgetDaoProvider) {
    this.budgetDaoProvider = budgetDaoProvider;
  }

  @Override
  public SelectBudgetViewModel get() {
    return provideInstance(budgetDaoProvider);
  }

  public static SelectBudgetViewModel provideInstance(Provider<BudgetDao> budgetDaoProvider) {
    return new SelectBudgetViewModel(budgetDaoProvider.get());
  }

  public static SelectBudgetViewModel_Factory create(Provider<BudgetDao> budgetDaoProvider) {
    return new SelectBudgetViewModel_Factory(budgetDaoProvider);
  }

  public static SelectBudgetViewModel newSelectBudgetViewModel(BudgetDao budgetDao) {
    return new SelectBudgetViewModel(budgetDao);
  }
}