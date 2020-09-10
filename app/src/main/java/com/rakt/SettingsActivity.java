package com.rakt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    Button logOutButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //buttons
        logOutButton=findViewById(R.id.logOutButton);

        bottomNavigationView=findViewById(R.id.bottomNavigationViewSettings);
        bottomNavigationView.setSelectedItemId(R.id.HomeActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.HomeActivity:
                        startActivity(new Intent(SettingsActivity.this,HomeActivity.class));
                        return true;
                    case R.id.settingsActivity:
                        return true;
                    case R.id.bloodBankActivity:
                        startActivity(new Intent(SettingsActivity.this,BloodBankActivity.class));
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logOutButton:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,MainActivity.class));
        }
    }
}