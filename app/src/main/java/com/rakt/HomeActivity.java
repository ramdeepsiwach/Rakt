package com.rakt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    protected Button searchButton, donorButton;
    ImageButton editProfileButton;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    BottomNavigationView bottomNavigationView;
    private static final int LOCATION_PERMISSION_CODE = 100;
    private static final int BACKGROUND_LOCATION_PERMISSION_CODE = 101;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Intialize button
        searchButton = findViewById(R.id.searchDialogButton);
        editProfileButton = findViewById(R.id.editProfileFromSearchBarButton);
        donorButton = findViewById(R.id.donorButton);

        //Set Click listeners
        editProfileButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        donorButton.setOnClickListener(this);

        //Database intialize
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        bottomNavigationView=findViewById(R.id.bottomNavigationViewHome);
        bottomNavigationView.setSelectedItemId(R.id.HomeActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.HomeActivity:
                        return true;
                    case R.id.settingsActivity:
                        startActivity(new Intent(HomeActivity.this,SettingsActivity.class));
                        return true;
                    case R.id.bloodBankActivity:
                        startActivity(new Intent(HomeActivity.this,BloodBankActivity.class));
                        return true;
                }
                return false;
            }
        });

        if ((ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            takeLocationPermission();
        }else{
            getLastKnownLocation();
        }
    }

    public void takeLocationPermission(){
        ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);
        ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(HomeActivity.this,
                        "Location Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
                getLastKnownLocation();
            } else {
                Toast.makeText(HomeActivity.this,
                        "Location Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
                takeLocationPermission();
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void getLastKnownLocation(){
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            setupMap();
                        }
                    }
                });

        /**/
    }

    public void setupMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchDialogButton:
                Toast.makeText(this,"Search Button Clicked",Toast.LENGTH_LONG).show();
                CustomDialogClass dialog=new CustomDialogClass(this);
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
                Toast.makeText(this,"Edit Profile Button Clicked",Toast.LENGTH_LONG).show();
                break;
            case R.id.donorButton:
                Toast.makeText(this,"Become a Donor Button Clicked",Toast.LENGTH_LONG).show();
                myRef = database.getReference("users/" + user.getUid());
                myRef.setValue("Donor");
                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Yes. This is you."));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLocation)      // Sets the center of the map to Mountain View
                .zoom(14)               // Sets the orientation of the camera to east
                .tilt(80)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}