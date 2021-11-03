package com.becker.foreger;

import java.lang.ref.Reference;

public class Mushroom {

    String Name;
    String Location;
    String Edible;
    String Description;
    //String Photo;

    public Mushroom(){}

    public Mushroom(String name, String location, String edible, String description) {
        Name = name;
        Location = location;
        Edible = edible;
        Description = description;
      //  Photo = photo;

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

   // public String getPhoto() {
   //     return Photo;
   // }

  //  public void setPhoto(String photo) {
   //     Photo = photo;
  //  }
}
