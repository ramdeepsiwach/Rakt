package com.rakt;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class CustomDialogClass extends Dialog implements View.OnClickListener {
    public Activity c;
    Button search;
    Spinner bloodDropDown,distanceDropDown;

    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.blood_search_dialog);
        search=findViewById(R.id.searchButton);
        search.setOnClickListener(this);

        //Blood Dropdown list
        bloodDropDown=findViewById(R.id.bloodGroudDropDown);
        String [] bloodGroupNames=new String[]{"A+","A-","B+","B-","O+","O-","AB+","AB-"};
        ArrayAdapter<String> bloodGroupAdapter=new ArrayAdapter<>(getContext(),R.layout.blood_group_options_list,bloodGroupNames);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        bloodDropDown.setAdapter(bloodGroupAdapter);

        //Distance DropdownList
        distanceDropDown=findViewById(R.id.dsitanceGroudDropDown);
        String [] distanceOptions=new String[]{"10 KM","20 KM","50KM","100 KM"};
        ArrayAdapter<String> distanceOptionAdapter=new ArrayAdapter<>(getContext(),R.layout.distance_options_list,distanceOptions);
        distanceOptionAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        distanceDropDown.setAdapter(distanceOptionAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.searchButton){
            //Todo: Add search button functionality
            String s1=bloodDropDown.getSelectedItem().toString();
            String s2=distanceDropDown.getSelectedItem().toString();
            Toast.makeText(getContext(),"Search tapped "+s1+" "+s2,Toast.LENGTH_LONG).show();
        }

    }
}
