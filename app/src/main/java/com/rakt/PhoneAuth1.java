package com.rakt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PhoneAuth1 extends AppCompatActivity {

    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth1);
        verify=findViewById(R.id.verify);

    }

    public void openPhoneAuth2(View view) {
        Intent i=new Intent(this,PhoneAuth2.class);
        startActivity(i);
    }
}