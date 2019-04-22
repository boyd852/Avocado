package app.doscervezas.avocado.di;

import androidx.fragment.app.Fragment;
import app.doscervezas.avocado.ui.fragments.SelectBudgetFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      FragmentBuildersModule_ContributeSelectBudgetFragment.SelectBudgetFragmentSubcomponent.class
)
public abstract class FragmentBuildersModule_ContributeSelectBudgetFragment {
  private FragmentBuildersModule_ContributeSelectBudgetFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(SelectBudgetFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      SelectBudgetFragmentSubcomponent.Builder builder);

  @Subcomponent
  public interface SelectBudgetFragmentSubcomponent extends AndroidInjector<SelectBudgetFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SelectBudgetFragment> {}
  }
}
