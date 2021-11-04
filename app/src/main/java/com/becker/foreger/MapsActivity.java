package com.becker.foreger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.becker.foreger.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.nio.charset.StandardCharsets;

public class MapsActivity extends AppCompatActivity {
 private static final String FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean permissionGranted = false;
    private static final int PERMISSION_CODE = 1;
    private FusedLocationProviderClient afusedLocationProviderClient;
    private GoogleMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getPermission();


/*
        Fragment fragment = new MapFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout,fragment)
                .commit();
*/
    }

    private void getLocation(){
        afusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    private void initMap(){
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                aMap = googleMap;

            }
        });

    }


    private void getPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
     if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE) == PackageManager.PERMISSION_GRANTED){
         if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE) == PackageManager.PERMISSION_GRANTED) {
             permissionGranted = true;
             initMap();
         }else{
             ActivityCompat.requestPermissions(this, permissions,1);
         }
    }else{
         ActivityCompat.requestPermissions(this, permissions,1);
     }

}


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionGranted = false;

        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            permissionGranted = false;
                            return;
                        }

                    }
                    permissionGranted = true;
                    initMap();
                }
            }

        }
    }

    private void getDeviceLocation(){
        afusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (permissionGranted) {
                Task location = afusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            Location location1 = (Location) task.getResult();

                        }

                    }
                });
            }


        }catch(SecurityException e){

    }
}

    private void moveCamera(LatLng latLng, float zoom){

    }
}