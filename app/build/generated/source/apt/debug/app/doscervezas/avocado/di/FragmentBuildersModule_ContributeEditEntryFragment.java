package app.doscervezas.avocado.di;

import androidx.fragment.app.Fragment;
import app.doscervezas.avocado.ui.fragments.EditEntryFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      FragmentBuildersModule_ContributeEditEntryFragment.EditEntryFragmentSubcomponent.class
)
public abstract class FragmentBuildersModule_ContributeEditEntryFragment {
  private FragmentBuildersModule_ContributeEditEntryFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(EditEntryFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      EditEntryFragmentSubcomponent.Builder builder);

  @Subcomponent
  public interface EditEntryFragmentSubcomponent extends AndroidInjector<EditEntryFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<EditEntryFragment> {}
  }
}
