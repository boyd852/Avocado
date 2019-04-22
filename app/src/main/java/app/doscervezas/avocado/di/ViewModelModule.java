package app.doscervezas.avocado.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import app.doscervezas.avocado.ui.ViewModelFactory;
import app.doscervezas.avocado.ui.viewmodels.AddBudgetViewModel;
import app.doscervezas.avocado.ui.viewmodels.AddEntryViewModel;
import app.doscervezas.avocado.ui.viewmodels.DetailFragmentViewModel;
import app.doscervezas.avocado.ui.viewmodels.EditBudgetViewModel;
import app.doscervezas.avocado.ui.viewmodels.EditEntryViewModel;
import app.doscervezas.avocado.ui.viewmodels.SelectBudgetViewModel;
import app.doscervezas.avocado.ui.viewmodels.SummaryViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddEntryViewModel.class)
    abstract ViewModel bindAddEntryViewModel(AddEntryViewModel addEntryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailFragmentViewModel.class)
    abstract ViewModel bindDetailFragmentViewModel(DetailFragmentViewModel detailFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditEntryViewModel.class)
    abstract ViewModel bindEditEntryViewModel(EditEntryViewModel editEntryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectBudgetViewModel.class)
    abstract ViewModel bindSelectBudgetViewModel(SelectBudgetViewModel selectBudgetViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddBudgetViewModel.class)
    abstract ViewModel bindAddBudgetViewModel(AddBudgetViewModel addBudgetViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditBudgetViewModel.class)
    abstract ViewModel bindEditBudgetViewModel(EditBudgetViewModel editBudgetViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SummaryViewModel.class)
    abstract ViewModel bindSummaryViewModel(SummaryViewModel summaryViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
