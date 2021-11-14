package com.becker.foreger;

import java.lang.ref.Reference;

public class Mushroom {

    /*************************
     *
     * Constructor to create a mushroom object
     *
     **************************/


    String Name;
    String Location;
    String Edible;
    String Description;

    public Mushroom() {
    }

    public Mushroom(String name, String location, String edible, String description) {
        Name = name;
        Location = location;
        Edible = edible;
        Description = description;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getEdible() {
        return Edible;
    }

    public void setEdible(String edible) {
        Edible = edible;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
