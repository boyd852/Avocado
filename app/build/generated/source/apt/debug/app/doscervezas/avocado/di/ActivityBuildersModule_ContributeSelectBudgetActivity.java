package app.doscervezas.avocado.di;

import android.app.Activity;
import app.doscervezas.avocado.ui.SelectBudgetActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      ActivityBuildersModule_ContributeSelectBudgetActivity.SelectBudgetActivitySubcomponent.class
)
public abstract class ActivityBuildersModule_ContributeSelectBudgetActivity {
  private ActivityBuildersModule_ContributeSelectBudgetActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(SelectBudgetActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      SelectBudgetActivitySubcomponent.Builder builder);

  @Subcomponent(modules = FragmentBuildersModule.class)
  public interface SelectBudgetActivitySubcomponent extends AndroidInjector<SelectBudgetActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SelectBudgetActivity> {}
  }
}
