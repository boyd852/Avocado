package app.doscervezas.avocado.ui;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.ui.fragments.AddBudgetFragment;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public class AddBudgetActivity extends DaggerAppCompatActivity implements HasSupportFragmentInjector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initiate fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.add_budget_content, new AddBudgetFragment())
                .commit();
    }
}
