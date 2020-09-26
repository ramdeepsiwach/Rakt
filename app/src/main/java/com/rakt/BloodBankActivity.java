package com.rakt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rakt.Database.Common;
import com.rakt.Database.CurrentUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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