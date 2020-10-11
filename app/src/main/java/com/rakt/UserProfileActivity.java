package com.rakt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rakt.Database.Common;

public class UserProfileActivity extends AppCompatActivity {
    TextView phoneNumber;
    EditText userName,userEmail,bloodGroup,userAge,userAddress;
    CheckBox isDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        phoneNumber=findViewById(R.id.phoneNumberTextView);
        userName=findViewById(R.id.userNameEditText);
        userEmail=findViewById(R.id.userEmailEditText);
        bloodGroup=findViewById(R.id.bloodGroupEditText);
        userAge=findViewById(R.id.userAgeEditText);
        userAddress=findViewById(R.id.userAddressEditText);
        isDonor=findViewById(R.id.donorCheckBox);

        //Toast.makeText(getApplicationContext(),Common.currentUser.getUserAddress(),Toast.LENGTH_LONG).show();

        if(Common.currentUser!=null)
            setProfileData();

        //Buttons and set on click listener
        findViewById(R.id.editProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.backToHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,HomeActivity.class));
            }
        });
    }

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
            bloodGroup.setText("");
        else
            bloodGroup.setText(Common.currentUser.getBloodGroup());

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