package com.becker.foreger;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

public class MushroomFeed {


    String MushroomFound;
    String Comment;

    public MushroomFeed(){}


    public MushroomFeed( String mushroomFound, String comment) {
        MushroomFound = mushroomFound;
        Comment = comment;
    }


    public String getMushroomFound() {
        return MushroomFound;
    }

    public void setMushroomFound(String mushroomFound) {
        MushroomFound = mushroomFound;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }




}
