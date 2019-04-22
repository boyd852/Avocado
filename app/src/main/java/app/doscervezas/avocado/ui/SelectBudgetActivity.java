package app.doscervezas.avocado.ui;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.ui.fragments.SelectBudgetFragment;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public class SelectBudgetActivity extends DaggerAppCompatActivity implements HasSupportFragmentInjector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_budget);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.select_budget_content, new SelectBudgetFragment())
                .commit();
    }
}
