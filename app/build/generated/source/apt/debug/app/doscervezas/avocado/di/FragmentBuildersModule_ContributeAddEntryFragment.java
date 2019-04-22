package app.doscervezas.avocado.di;

import androidx.fragment.app.Fragment;
import app.doscervezas.avocado.ui.fragments.AddEntryFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      FragmentBuildersModule_ContributeAddEntryFragment.AddEntryFragmentSubcomponent.class
)
public abstract class FragmentBuildersModule_ContributeAddEntryFragment {
  private FragmentBuildersModule_ContributeAddEntryFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(AddEntryFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      AddEntryFragmentSubcomponent.Builder builder);

  @Subcomponent
  public interface AddEntryFragmentSubcomponent extends AndroidInjector<AddEntryFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AddEntryFragment> {}
  }
}
