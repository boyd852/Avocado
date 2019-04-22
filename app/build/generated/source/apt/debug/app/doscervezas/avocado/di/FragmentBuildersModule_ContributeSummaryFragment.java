package app.doscervezas.avocado.di;

import androidx.fragment.app.Fragment;
import app.doscervezas.avocado.ui.fragments.SummaryFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = FragmentBuildersModule_ContributeSummaryFragment.SummaryFragmentSubcomponent.class
)
public abstract class FragmentBuildersModule_ContributeSummaryFragment {
  private FragmentBuildersModule_ContributeSummaryFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(SummaryFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      SummaryFragmentSubcomponent.Builder builder);

  @Subcomponent
  public interface SummaryFragmentSubcomponent extends AndroidInjector<SummaryFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SummaryFragment> {}
  }
}
