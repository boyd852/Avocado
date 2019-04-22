package app.doscervezas.avocado.di;

import android.app.Activity;
import app.doscervezas.avocado.ui.AddBudgetActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      ActivityBuildersModule_ContributeAddBudgetActivity.AddBudgetActivitySubcomponent.class
)
public abstract class ActivityBuildersModule_ContributeAddBudgetActivity {
  private ActivityBuildersModule_ContributeAddBudgetActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(AddBudgetActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      AddBudgetActivitySubcomponent.Builder builder);

  @Subcomponent(modules = FragmentBuildersModule.class)
  public interface AddBudgetActivitySubcomponent extends AndroidInjector<AddBudgetActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AddBudgetActivity> {}
  }
}
