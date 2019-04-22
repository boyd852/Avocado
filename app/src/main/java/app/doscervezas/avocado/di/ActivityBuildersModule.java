package app.doscervezas.avocado.di;

import app.doscervezas.avocado.ui.AddBudgetActivity;
import app.doscervezas.avocado.ui.EditBudgetActivity;
import app.doscervezas.avocado.ui.EditEntryActivity;
import app.doscervezas.avocado.ui.MainActivity;
import app.doscervezas.avocado.ui.SelectBudgetActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract EditEntryActivity contributeEditEntryActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract SelectBudgetActivity contributeSelectBudgetActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract AddBudgetActivity contributeAddBudgetActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract EditBudgetActivity contributeEditBudgetActivity();
}