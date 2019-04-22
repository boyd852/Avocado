package app.doscervezas.avocado.di;

import androidx.fragment.app.Fragment;
import app.doscervezas.avocado.ui.fragments.EditBudgetFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      FragmentBuildersModule_ContributeEditBudgetFragment.EditBudgetFragmentSubcomponent.class
)
public abstract class FragmentBuildersModule_ContributeEditBudgetFragment {
  private FragmentBuildersModule_ContributeEditBudgetFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(EditBudgetFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      EditBudgetFragmentSubcomponent.Builder builder);

  @Subcomponent
  public interface EditBudgetFragmentSubcomponent extends AndroidInjector<EditBudgetFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<EditBudgetFragment> {}
  }
}
