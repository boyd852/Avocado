package app.doscervezas.avocado.di;

import app.doscervezas.avocado.ui.EditBudgetActivity;
import app.doscervezas.avocado.ui.fragments.AddBudgetFragment;
import app.doscervezas.avocado.ui.fragments.AddEntryFragment;
import app.doscervezas.avocado.ui.fragments.DetailFragment;
import app.doscervezas.avocado.ui.fragments.EditBudgetFragment;
import app.doscervezas.avocado.ui.fragments.EditEntryFragment;
import app.doscervezas.avocado.ui.fragments.SelectBudgetFragment;
import app.doscervezas.avocado.ui.fragments.SummaryFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract AddEntryFragment contributeAddEntryFragment();

    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();

    @ContributesAndroidInjector
    abstract SelectBudgetFragment contributeSelectBudgetFragment();

    @ContributesAndroidInjector
    abstract AddBudgetFragment contributeAddBudgetFragment();

    @ContributesAndroidInjector
    abstract EditBudgetFragment contributeEditBudgetFragment();

    @ContributesAndroidInjector
    abstract SummaryFragment contributeSummaryFragment();

    @ContributesAndroidInjector
    abstract EditEntryFragment contributeEditEntryFragment();
}