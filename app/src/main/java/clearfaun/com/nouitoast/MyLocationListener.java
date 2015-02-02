package clearfaun.com.nouitoast;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by spencer on 1/28/2015.
 */
public class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location locFromGps) {

      /*  int accuracy = (int) locFromGps.getAccuracy();
        long time = System.currentTimeMillis();

        Log.i("MyActivity12", "MyLocationListener: DO I WORK " + accuracy);
        if (locFromGps.getAccuracy() < 10 ) {



            Log.i("MyActivity12", "MyLocationListener: DOES MY ACCURACY CHANGE? " + accuracy);

            GpsToAddress task = new GpsToAddress();
            task.execute();
            Log.i("MyActivity12", "after GpsToAddress = " + accuracy);

        }*/





        // called when the listener is notified with a location update from the GPS
    }
    public static void whenLocationChanged(Location location) {
        MainActivity.latatude =  location.getLatitude();
        MainActivity.longitude =  location.getLongitude();
        //editText.setText(String.valueOf(latatude) + "\n" + String.valueOf(longitude));

    }


    @Override
    public void onProviderDisabled(String provider) {
        // called when the GPS provider is turned off (user turning off the GPS on the phone)
    }

    @Override
    public void onProviderEnabled(String provider) {
        // called when the GPS provider is turned on (user turning on the GPS on the phone)
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // called when the status of the GPS provider changes
    }
}