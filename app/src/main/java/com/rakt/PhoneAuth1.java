package com.rakt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PhoneAuth1 extends AppCompatActivity {

    private EditText phoneEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth1);

        phoneEditText = findViewById(R.id.phoneEditText);
        findViewById(R.id.requestVerification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String phoneNumber = phoneEditText.getText().toString().trim();
                if(phoneNumber.isEmpty() || phoneNumber.length() < 10){
                    phoneEditText.setError("Enter a valid Phone Number");
                    phoneEditText.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneAuth1.this, PhoneAuth2.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        });

    }

}