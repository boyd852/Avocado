package app.doscervezas.avocado.ui;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.doscervezas.avocado.R;
import app.doscervezas.avocado.ui.fragments.EditEntryFragment;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public class EditEntryActivity extends DaggerAppCompatActivity implements HasSupportFragmentInjector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Entry");

        //Initiate fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.edit_entry_content, new EditEntryFragment())
                .commit();
    }
}



