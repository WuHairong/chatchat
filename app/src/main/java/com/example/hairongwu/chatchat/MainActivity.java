package com.example.hairongwu.chatchat;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    public static String LOG_TAG = "My log tag";
    public static String nickname;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;

    private LocationData locationData = LocationData.getLocationData();//store location to share between activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    @Override
    public void onResume(){

        //Log.i(LOG_TAG, "Inside resume of main activity");

        if(locationData.getLocation()== null){
            requestLocationUpdate();
        }


        super.onResume();
    }





    /*
	Request location update. This must be called in onResume if the user has allowed location sharing
	 */
    private void requestLocationUpdate(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null &&
                (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 35000, 10, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, locationListener);

                Log.i(LOG_TAG, "requesting location update");
            }
            else {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Log.i(LOG_TAG, "please allow to use your location");

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        } else{
            //Log.i(LOG_TAG, "requesting location update from user");
            //prompt user to enable location
            Intent gpsOptionsIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {

                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 35000, 10, locationListener);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, locationListener);

                        //Log.i(LOG_TAG, "requesting location update");
                    } else{
                        throw new RuntimeException("permission not granted still callback fired");
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    /*
	Remove location update. This must be called in onPause if the user has allowed location sharing
	 */
    private void removeLocationUpdate() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                locationManager.removeUpdates(locationListener);
                Log.i(LOG_TAG, "removing location update");
            }
        }
    }


    /**
     * Listens to the location, and gets the most precise recent location.
     * Copied from Prof. Luca class code
     */
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            Location lastLocation = locationData.getLocation();

            // Do something with the location you receive.
            double newAccuracy = location.getAccuracy();

            long newTime = location.getTime();
            // Is this better than what we had?  We allow a bit of degradation in time.
            boolean isBetter = ((lastLocation == null) ||
                    newAccuracy < lastLocation.getAccuracy() + (newTime - lastLocation.getTime()));

            //if(isBetter){
            //    Log.i(LOG_TAG, "8888888: "+ "True");
            //}else{
            //    Log.i(LOG_TAG, "8888888: "+"False");
            //}
            if (isBetter) {
                // We replace the old estimate by this one.
                locationData.setLocation(location);
                //Log.i(LOG_TAG, "8888888: " + locationData.getLocation());
                //Log.i(LOG_TAG, "8888888: " + locationData.getLocation().getLatitude());
                //Log.i(LOG_TAG, "8888888: " + locationData.getLocation().getLatitude());
                //Log.i(LOG_TAG, "8888888: " + locationData.getLocation().getLatitude());
                //Log.i(LOG_TAG, "9999999: " + locationData.getLocation().getLongitude());
                //Log.i(LOG_TAG, "9999999: " + locationData.getLocation().getLongitude());
                //Log.i(LOG_TAG, "9999999: " + locationData.getLocation().getLongitude());
                //Log.i(LOG_TAG, "9999999: " + locationData.getLocation().getLongitude());

                //Now we have the location.
                //Button searchButton = (Button) findViewById(R.id.searchButton);
                //if(checkLocationAllowed())
                //    searchButton.setEnabled(true);//We must enable search button
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    public void search(View v){

        Intent intent = new Intent(this, SearchActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText2);
        nickname = editText.getText().toString();
        intent.putExtra(nickname, editText.getText().toString());
        //Log.i(LOG_TAG, "44444: " + editText.getText().toString());
        //Log.i(LOG_TAG, "44444: " + editText.getText().toString());
        //Log.i(LOG_TAG, "44444: " + editText.getText().toString());
        //Log.i(LOG_TAG, "44444: " + editText.getText().toString());
        //Log.i(LOG_TAG, "44444: " + editText.getText().toString());
        startActivity(intent);//pass the cuisine to the search activity for searching
    }

}