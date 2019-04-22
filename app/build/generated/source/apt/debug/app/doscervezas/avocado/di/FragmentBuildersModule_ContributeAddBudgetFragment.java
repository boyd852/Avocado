package app.doscervezas.avocado.di;

import androidx.fragment.app.Fragment;
import app.doscervezas.avocado.ui.fragments.AddBudgetFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      FragmentBuildersModule_ContributeAddBudgetFragment.AddBudgetFragmentSubcomponent.class
)
public abstract class FragmentBuildersModule_ContributeAddBudgetFragment {
  private FragmentBuildersModule_ContributeAddBudgetFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(AddBudgetFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      AddBudgetFragmentSubcomponent.Builder builder);

  @Subcomponent
  public interface AddBudgetFragmentSubcomponent extends AndroidInjector<AddBudgetFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AddBudgetFragment> {}
  }
}
