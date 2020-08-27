package com.rakt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhoneAuth2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth2);
    }
    public void openPhoneAuth1FromPhoneAuth2(View view) {
        Intent i=new Intent(this,PhoneAuth1.class);
        startActivity(i);
    }
}