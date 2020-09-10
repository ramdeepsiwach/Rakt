package com.rakt;

import android.Manifest;
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
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    private static final int PERMISSIONS_REQUEST = 100;
    LocationManager mLocationManager;
    long LOCATION_REFRESH_TIME = 1000;
    float LOCATION_REFRESH_DISTANCE = 0;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(this);

        /*if (ActivityCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(HomeActivity.this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},44);

        }*/
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
    /* private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location!=null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions options=new MarkerOptions().position(latLng)
                                    .title("I am there");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,50));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==44){
            if(grantResults.length>0 & grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    } */
    @Override
    public void onMapReady(GoogleMap mMap) {
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}