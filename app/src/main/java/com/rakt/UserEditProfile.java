package com.rakt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rakt.Database.Common;

import java.util.HashMap;
import java.util.Map;

public class UserEditProfile extends AppCompatActivity implements View.OnClickListener {
    TextView phoneNumber;
    EditText userNameEditText,userEmailEditText,bloodGroupEditText,userAgeEditText,userAddressEditText;
    CheckBox donorCheckBox;
    Button saveButton,cancelButton,backButton;
    FirebaseUser mAuth=FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        //Intiate variables
        phoneNumber=findViewById(R.id.phoneNumber);
        userNameEditText=findViewById(R.id.userNameEditText);
        userEmailEditText=findViewById(R.id.userEmailEditText);
        bloodGroupEditText=findViewById(R.id.bloodGroupEditText);
        userAgeEditText=findViewById(R.id.userAgeEditText);
        userAddressEditText=findViewById(R.id.userAddressEditText);
        donorCheckBox=findViewById(R.id.donorCheckBox);

        //initialize buttons
        saveButton=findViewById(R.id.saveButton);
        cancelButton=findViewById(R.id.cancelButton);
        backButton=findViewById(R.id.backFromEditProfileToSettings);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        if(Common.currentUser!=null)
            setProfile();

    }
    //Set initial profile
    private void setProfile() {
        phoneNumber.setText(new StringBuilder("+91 ").append(Common.currentUser.getPhoneNumber()));

        if(Common.currentUser.getUserName()==null || Common.currentUser.getUserName().equals("NA"))
            userNameEditText.setText("");
        else
            userNameEditText.setText(Common.currentUser.getUserName());

        if(Common.currentUser.getUserEmail()==null || Common.currentUser.getUserEmail().equals("NA"))
            userEmailEditText.setText("");
        else
            userEmailEditText.setText(Common.currentUser.getUserEmail());

        if(Common.currentUser.getBloodGroup()==null || Common.currentUser.getBloodGroup().equals("NA"))
            bloodGroupEditText.setText("");
        else
            bloodGroupEditText.setText(Common.currentUser.getBloodGroup());

        if(Common.currentUser.getUserAge()==null || Common.currentUser.getUserAge().equals("NA"))
            userAgeEditText.setText("");
        else
            userAgeEditText.setText(Common.currentUser.getUserAge());

        if(Common.currentUser.getUserAddress()==null || Common.currentUser.getUserAddress().equals("NA"))
            userAddressEditText.setText("");
        else
            userAddressEditText.setText(Common.currentUser.getUserAddress());

        if(Common.currentUser.getDonor()==null || Common.currentUser.getDonor().equals("false"))
            donorCheckBox.setChecked(false);
        else
            donorCheckBox.setChecked(true);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backFromEditProfileToSettings:
                startActivity(new Intent(UserEditProfile.this,SettingsActivity.class));
                break;
            case R.id.saveButton:
                updateProfile();
                break;
            case R.id.cancelButton:
                startActivity(new Intent(UserEditProfile.this,SettingsActivity.class));
                break;
        }
    }

    private void updateProfile() {
        FirebaseDatabase.getInstance().getReference(Common.USER_REF).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Map<String,Object> updateData=new HashMap<>();
                updateData.put("userName",userNameEditText.getText().toString());
                Common.currentUser.setUserName(userNameEditText.getText().toString());

                updateData.put("userEmail",userEmailEditText.getText().toString());
                Common.currentUser.setUserEmail(userEmailEditText.getText().toString());

                updateData.put("bloodGroup",bloodGroupEditText.getText().toString());
                Common.currentUser.setBloodGroup(bloodGroupEditText.getText().toString());

                updateData.put("userAge",userAgeEditText.getText().toString());
                Common.currentUser.setUserAge(userAgeEditText.getText().toString());

                updateData.put("userAddress",userAddressEditText.getText().toString());
                Common.currentUser.setUserAddress(userAddressEditText.getText().toString());

                if(donorCheckBox.isChecked()){
                    updateData.put("donor","true");
                    Common.currentUser.setDonor("true");
                }else{
                    updateData.put("donor","false");
                    Common.currentUser.setDonor("false");
                }
                snapshot.getRef().updateChildren(updateData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Database Updated !",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
    }
}