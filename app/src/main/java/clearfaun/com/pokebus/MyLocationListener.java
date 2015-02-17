package clearfaun.com.pokebus;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by spencer on 1/28/2015.
 */
public class MyLocationListener implements LocationListener {

    int accuracy;


    @Override
    public void onLocationChanged(Location location) {

        accuracy = (int) location.getAccuracy();

        Log.i("MyLocationListener", "IN  onLocationChanged !!!!!!!!!!!!" + accuracy);


    }



    @Override
    public void onProviderDisabled(String provider) {
        // called when the GPS provider is turned off (user turning off the GPS on the phone)
        Log.i("MyLocationListener", "onProviderDisabled " + accuracy);
    }

    @Override
    public void onProviderEnabled(String provider) {
        // called when the GPS provider is turned on (user turning on the GPS on the phone)
        Log.i("MyLocationListener", "onProviderEnabled  " + accuracy);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // called when the status of the GPS provider changes
        Log.i("MyLocationListener", "onStatusChanged" + accuracy);

    }
}