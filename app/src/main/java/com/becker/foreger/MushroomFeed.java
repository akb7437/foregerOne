package com.becker.foreger;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

public class MushroomFeed {

    /*************************
     *
     * Constructor to create a mushroom Feed object
     *
     **************************/

    String MushroomFound;
    String Name;



    public MushroomFeed(){}

    public MushroomFeed(String mushroomFound, String name) {
        MushroomFound = mushroomFound;
        Name = name;
    }

    public String getMushroomFound() {
        return MushroomFound;
    }

    public void setMushroomFound(String mushroomFound) {
        MushroomFound = mushroomFound;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
