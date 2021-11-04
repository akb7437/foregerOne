package com.becker.foreger;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

public class MushroomFeed {


    String MushroomFound;



    public MushroomFeed(){}


    public MushroomFeed( String mushroomFound ) {
        MushroomFound = mushroomFound;


    }


    public String getMushroomFound() {
        return MushroomFound;
    }

    public void setMushroomFound(String mushroomFound) {
        MushroomFound = mushroomFound;
    }


}
