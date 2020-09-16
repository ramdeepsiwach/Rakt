package com.rakt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Buttons and set on click listener
        findViewById(R.id.editProfileFromUserProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,UserEditProfile.class));
            }
        });

        findViewById(R.id.backFromUserProfileToHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,HomeActivity.class));
            }
        });
    }
}