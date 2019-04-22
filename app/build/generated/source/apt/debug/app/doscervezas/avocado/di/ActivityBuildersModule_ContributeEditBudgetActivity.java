package app.doscervezas.avocado.di;

import android.app.Activity;
import app.doscervezas.avocado.ui.EditBudgetActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      ActivityBuildersModule_ContributeEditBudgetActivity.EditBudgetActivitySubcomponent.class
)
public abstract class ActivityBuildersModule_ContributeEditBudgetActivity {
  private ActivityBuildersModule_ContributeEditBudgetActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(EditBudgetActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      EditBudgetActivitySubcomponent.Builder builder);

  @Subcomponent(modules = FragmentBuildersModule.class)
  public interface EditBudgetActivitySubcomponent extends AndroidInjector<EditBudgetActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<EditBudgetActivity> {}
  }
}
