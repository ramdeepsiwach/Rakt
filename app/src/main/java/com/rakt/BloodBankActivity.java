package com.rakt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BloodBankActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewBloodBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        bottomNavigationViewBloodBank=findViewById(R.id.bottomNavigationViewBloodBank);

        bottomNavigationViewBloodBank.setSelectedItemId(R.id.bloodBankActivity);
        bottomNavigationViewBloodBank.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.HomeActivity:
                        startActivity(new Intent(BloodBankActivity.this,HomeActivity.class));
                        return true;
                    case R.id.settingsActivity:
                        startActivity(new Intent(BloodBankActivity.this,SettingsActivity.class));
                        return true;
                    case R.id.bloodBankActivity:
                        return true;
                }
                return false;
            }
        });
    }
}