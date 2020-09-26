package com.rakt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rakt.Database.Common;
import com.rakt.Database.CurrentUser;

import java.util.Currency;

public class MainActivity extends AppCompatActivity {
    CurrentUser user;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

        if (mAuth!= null) {
            db= FirebaseDatabase.getInstance().getReference(Common.USER_REF);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user=snapshot.child(mAuth.getUid()).getValue(CurrentUser.class);
                    Common.currentUser=user;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);
    }
    public void openPhoneAuth1(View view) {
        startActivity(new Intent(this,PhoneAuth1.class));
    }
}