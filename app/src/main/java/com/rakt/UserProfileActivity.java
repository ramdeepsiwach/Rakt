package com.rakt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rakt.Database.Common;

public class UserProfileActivity extends AppCompatActivity {
    TextView phoneNumber,userName,userEmail,bloodGroupProfile,userAge,userAddress,donorMessageTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //define all the textViews
        phoneNumber=findViewById(R.id.phoneNumberTextView);
        userName=findViewById(R.id.userNameTextView);
        userEmail=findViewById(R.id.userEmailTextView);
        bloodGroupProfile=findViewById(R.id.bloodGroupProfileTextView);
        userAge=findViewById(R.id.userAgeTextView);
        userAddress=findViewById(R.id.userAddressTextView);
        donorMessageTextview=findViewById(R.id.donorMessageTextview);

        //Toast.makeText(getApplicationContext(),Common.currentUser.getUserAddress(),Toast.LENGTH_LONG).show();

        if(Common.currentUser!=null)
            setProfileData();

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

    //Set data to profile textviews
    private void setProfileData() {
        phoneNumber.setText(new StringBuilder("+91 ").append(Common.currentUser.getPhoneNumber()));

        if(Common.currentUser.getUserName()==null || Common.currentUser.getUserName().equals("NA"))
            userName.setText("");
        else
            userName.setText(Common.currentUser.getUserName());

        if(Common.currentUser.getUserEmail()==null || Common.currentUser.getUserEmail().equals("NA"))
            userEmail.setText("");
        else
            userEmail.setText(Common.currentUser.getUserEmail());

        if(Common.currentUser.getBloodGroup()==null || Common.currentUser.getBloodGroup().equals("NA"))
            bloodGroupProfile.setText("");
        else
            bloodGroupProfile.setText(Common.currentUser.getBloodGroup());

        if(Common.currentUser.getUserAge()==null || Common.currentUser.getUserAge().equals("NA"))
            userAge.setText("");
        else
            userAge.setText(Common.currentUser.getUserAge());

        if(Common.currentUser.getUserAddress()==null || Common.currentUser.getUserAddress().equals("NA"))
            userAddress.setText("");
        else
            userAddress.setText(Common.currentUser.getUserAddress());
    }
}