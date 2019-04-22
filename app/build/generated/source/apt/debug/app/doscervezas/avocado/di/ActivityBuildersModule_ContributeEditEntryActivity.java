package app.doscervezas.avocado.di;

import android.app.Activity;
import app.doscervezas.avocado.ui.EditEntryActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      ActivityBuildersModule_ContributeEditEntryActivity.EditEntryActivitySubcomponent.class
)
public abstract class ActivityBuildersModule_ContributeEditEntryActivity {
  private ActivityBuildersModule_ContributeEditEntryActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(EditEntryActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      EditEntryActivitySubcomponent.Builder builder);

  @Subcomponent(modules = FragmentBuildersModule.class)
  public interface EditEntryActivitySubcomponent extends AndroidInjector<EditEntryActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<EditEntryActivity> {}
  }
}
