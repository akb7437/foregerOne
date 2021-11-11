package com.becker.foreger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaDrm;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.becker.foreger.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;

public class MapsActivity extends AppCompatActivity {
    private static final String FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean permissionGranted = false;
    private static final int PERMISSION_CODE = 1;
    private FusedLocationProviderClient afusedLocationProviderClient;
    public  GoogleMap aMap;
    String newName;
    String newComment;


// On create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getPermission();


    }

// Get location method
    private void getLocation() {
        afusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {

            @SuppressLint("MissingPermission")
            Task location = afusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MapsActivity.this, "Found location", Toast.LENGTH_SHORT).show();
                        Location currentLocation = (Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15f);
                    } else {
                        Toast.makeText(MapsActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (SecurityException e) {
            Toast.makeText(MapsActivity.this, "Location not found", Toast.LENGTH_SHORT).show();

        }

    }

// Init map
    private void initMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Toast.makeText(MapsActivity.this, "Map Ready", Toast.LENGTH_SHORT).show();
                aMap = googleMap;

                MarkerOptions markerOptions = new MarkerOptions();

                FirebaseFirestore mydb = FirebaseFirestore.getInstance();
                DocumentReference mushdata = mydb.collection("Maps data").document();




                if (permissionGranted) {
                    getLocation();

                    aMap.setMyLocationEnabled(true);
                    Toast.makeText(MapsActivity.this, "congrats", Toast.LENGTH_SHORT).show();




                }
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng1) {


                        View view = (LayoutInflater.from(MapsActivity.this))
                                .inflate(R.layout.dialog_layout, null);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
                        dialog.setView(view);
                        final EditText mname = (EditText) view.findViewById(R.id.mushfoundedt);
                        final EditText mcomment = (EditText) view.findViewById(R.id.commentedt);


                        dialog.setCancelable(true);
                        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                newName = mname.getText().toString();
                                newComment = mcomment.getText().toString();


                                MushroomFeed mushfeed = new MushroomFeed();
                                mushfeed.setName(newName);
                                mushfeed.setMushroomFound(newComment);
                                mushdata.set(mushfeed).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MapsActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                markerOptions.position(latLng1);
                                markerOptions.title(newName);
                                markerOptions.snippet(mcomment.toString());
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 20f));
                                googleMap.addMarker(markerOptions);
                            }
                        });
                        Dialog dialog1 = dialog.create();
                        dialog1.show();


                    }
                });

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


    @SuppressLint("MissingSuperCall")
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
                    Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    initMap();

                }
            }

        }
    }




    private void moveCamera(LatLng latLng, float zoom){
    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

}
