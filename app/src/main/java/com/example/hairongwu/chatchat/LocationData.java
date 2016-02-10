package com.example.hairongwu.chatchat;
import android.location.Location;

/**
 * Created by hairongwu on 2/10/16.
 */
public class LocationData {
    private static LocationData instance = null;

    private LocationData(){}

    private Location location;


    public Location getLocation(){
        return location;
    }

    public void setLocation(Location _location){
        location = _location;
    }

    public static LocationData getLocationData(){
        if(instance == null){
            instance = new LocationData();
        }
        return instance;
    }

}
