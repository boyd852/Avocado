package app.doscervezas.avocado.di;

import androidx.fragment.app.Fragment;
import app.doscervezas.avocado.ui.fragments.DetailFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = FragmentBuildersModule_ContributeDetailFragment.DetailFragmentSubcomponent.class
)
public abstract class FragmentBuildersModule_ContributeDetailFragment {
  private FragmentBuildersModule_ContributeDetailFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(DetailFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      DetailFragmentSubcomponent.Builder builder);

  @Subcomponent
  public interface DetailFragmentSubcomponent extends AndroidInjector<DetailFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DetailFragment> {}
  }
}
