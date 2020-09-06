package com.rakt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class searchFragment extends Fragment implements View.OnClickListener {
    protected Button searchButton,donorButton;
    ImageButton editProfileButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        searchButton= view.findViewById(R.id.searchDialogButton);
        editProfileButton= view.findViewById(R.id.editProfileFromSearchBarButton);
        editProfileButton.setOnClickListener(searchFragment.this);
        donorButton= view.findViewById(R.id.donorButton);
        searchButton.setOnClickListener(searchFragment.this);
        donorButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchDialogButton:
                Toast.makeText(getContext(),"Search Button Clicked",Toast.LENGTH_LONG).show();
                CustomDialogClass dialog=new CustomDialogClass(getActivity());
                Window window=dialog.getWindow();
                assert window != null;
                WindowManager.LayoutParams wlp=window.getAttributes();
                wlp.gravity= Gravity.TOP;
                wlp.verticalMargin=0.2f;
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                window.setAttributes(wlp);
                dialog.show();

                break;

            case R.id.editProfileFromSearchBarButton:
                Toast.makeText(getContext(),"Edit Profile Button Clicked",Toast.LENGTH_LONG).show();
                break;

            case R.id.donorButton:
                Toast.makeText(getContext(),"Become a Donor Button Clicked",Toast.LENGTH_LONG).show();
                break;

        }

    }
}