package app.doscervezas.avocado.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import app.doscervezas.avocado.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        //Get current budget and update fields
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long currentBudgetId = sharedPreferences.getLong("BUDGET_ID", 0);
        if(currentBudgetId == 0){
            loadSelectBudgetActivity();
        } else {
            loadBudgetActivity();
        }
    }

    public void loadSelectBudgetActivity(){
        Intent intent = new Intent(this, SelectBudgetActivity.class);
        startActivity(intent);
    }

    public void loadBudgetActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
