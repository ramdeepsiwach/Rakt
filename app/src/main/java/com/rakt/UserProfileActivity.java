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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView phoneNumber;
    EditText userName, userEmail, bloodGroup, userAge, userAddress;
    CheckBox isDonor;
    Button saveButton, cancelButton, backButton, editProfileButton;
    FirebaseUser mAuth= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Initiate Fields
        phoneNumber=findViewById(R.id.phoneNumberTextView);
        userName=findViewById(R.id.userNameEditText);
        userEmail=findViewById(R.id.userEmailEditText);
        bloodGroup=findViewById(R.id.bloodGroupEditText);
        userAge=findViewById(R.id.userAgeEditText);
        userAddress=findViewById(R.id.userAddressEditText);
        isDonor=findViewById(R.id.donorCheckBox);

        saveButton=findViewById(R.id.saveButton);
        cancelButton=findViewById(R.id.cancelButton);
        backButton=findViewById(R.id.backToHomeButton);
        editProfileButton=findViewById(R.id.editProfileButton);
        saveButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        if(Common.currentUser!=null)
            setProfileData();

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

        if(Common.currentUser.getDonor().contentEquals("true"))
            isDonor.setChecked(true);
        else
            isDonor.setChecked(false);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backToHomeButton:
                startActivity(new Intent(UserProfileActivity.this,HomeActivity.class));
                break;
            case R.id.saveButton:
                updateProfile();
                break;
            case R.id.cancelButton:
                makeFieldsEditable(false);
                break;
            case R.id.editProfileButton:
                makeFieldsEditable(true);
                break;
        }
    }

    private void makeFieldsEditable(boolean editable) {
        userName.setEnabled(editable);
        userEmail.setEnabled(editable);
        bloodGroup.setEnabled(editable);
        userAge.setEnabled(editable);
        userAddress.setEnabled(editable);
        isDonor.setEnabled(editable);

        editProfileButton.setEnabled(!editable);
        saveButton.setEnabled(editable);
        cancelButton.setEnabled(editable);
        editProfileButton.setVisibility(editable?View.INVISIBLE:View.VISIBLE);
        saveButton.setVisibility(editable?View.VISIBLE:View.INVISIBLE);
        cancelButton.setVisibility(editable?View.VISIBLE:View.INVISIBLE);
    }

    private void updateProfile() {
        Set<String> bloodGroups = new HashSet<>();
        bloodGroups.add("A+");
        bloodGroups.add("A-");
        bloodGroups.add("B+");
        bloodGroups.add("B-");
        bloodGroups.add("AB+");
        bloodGroups.add("AB-");
        bloodGroups.add("O+");
        bloodGroups.add("O-");
        final String uName = userName.getText().toString(), uEmail = userEmail.getText().toString(), uBloodGroup = bloodGroup.getText().toString(),
                uAge = userAge.getText().toString(), uAddress = userAddress.getText().toString();

        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(uEmail);
        if(!matcher.matches()){
            Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
            setProfileData();
            return;
        }
        if(!bloodGroups.contains(uBloodGroup)){
            Toast.makeText(getApplicationContext(),"Invalid Blood Group",Toast.LENGTH_SHORT).show();
            setProfileData();
            return;
        }
        if(Integer.parseInt(uAge)<18){
            Toast.makeText(getApplicationContext(),"You are too young to join",Toast.LENGTH_SHORT).show();
            setProfileData();
            return;
        }

        FirebaseDatabase.getInstance().getReference(Common.USER_REF).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Map<String,Object> updateData=new HashMap<>();
                updateData.put("userName", uName);
                Common.currentUser.setUserName(uName);

                updateData.put("userEmail", uEmail);
                Common.currentUser.setUserEmail(uEmail);

                updateData.put("bloodGroup", uBloodGroup);
                Common.currentUser.setBloodGroup(uBloodGroup);

                updateData.put("userAge", uAge);
                Common.currentUser.setUserAge(uAge);

                updateData.put("userAddress", uAddress);
                Common.currentUser.setUserAddress(uAddress);

                if(isDonor.isChecked()){
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