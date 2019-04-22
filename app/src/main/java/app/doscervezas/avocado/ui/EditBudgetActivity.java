package app.doscervezas.avocado.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.ui.fragments.EditBudgetFragment;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public class EditBudgetActivity extends DaggerAppCompatActivity implements HasSupportFragmentInjector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Budget");

        //Initiate fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.edit_budget_content, new EditBudgetFragment())
                .commit();
    }
}
