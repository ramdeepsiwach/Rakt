package com.rakt;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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

import android.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;

public class searchFragment extends Fragment implements View.OnClickListener {
    protected Button searchButton, donorButton;
    ImageButton editProfileButton;
    private MapView mapView;
    private GoogleMap googleMap;
    private static final int PERMISSIONS_REQUEST = 100;
    LocationManager mLocationManager;
    long LOCATION_REFRESH_TIME = 1000;
    float LOCATION_REFRESH_DISTANCE = 0;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchButton = view.findViewById(R.id.searchDialogButton);
        editProfileButton = view.findViewById(R.id.editProfileFromSearchBarButton);
        editProfileButton.setOnClickListener(searchFragment.this);
        donorButton = view.findViewById(R.id.donorButton);
        searchButton.setOnClickListener(searchFragment.this);
        donorButton.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(requireContext().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST);
                }
                googleMap.setMyLocationEnabled(true);
                mLocationManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull final Location location) {
                        LatLng myLoc = new LatLng(location.getLatitude(),location.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(myLoc).title("Title").snippet("Marker Description"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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
                myRef = database.getReference("users/" + user.getUid());
                myRef.setValue("Donor");
                break;

        }

    }
}