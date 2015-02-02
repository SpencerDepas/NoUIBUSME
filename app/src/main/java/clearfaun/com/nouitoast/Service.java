package clearfaun.com.nouitoast;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;


/**
 * Created by spencer on 2/1/2015.
 */
public class Service extends IntentService {


    private static LocationManager locationManager;
    private static String provider;



    public Service() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        // Handle events on worker thread here

        Log.i("MyActivity12", "onHandleIntent " );

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);

        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);



        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);





        int accuracy = (int) location.getAccuracy();
        long time = System.currentTimeMillis();
        Log.i("MyActivity12", "before while accuracy loop — accuracy = " + accuracy);
        while (accuracy > 10 ) {


            accuracy = (int) location.getAccuracy();

            if(System.currentTimeMillis() >= time + 5000){
                break;
            }

        }
        Log.i("MyActivity12", "after while accuracy loop — accuracy = " + accuracy);


        // Initialize the location fields
        if (location != null) {

            onLocationChanged(location);

        }



        GpsToAddress task = new GpsToAddress();
        task.execute();
        Log.i("MyActivity12", "after GpsToAddress = " + accuracy);


    }


    public static void onLocationChanged(Location location) {
        MainActivity.latatude =  location.getLatitude();
        MainActivity.longitude =  location.getLongitude();
        //editText.setText(String.valueOf(latatude) + "\n" + String.valueOf(longitude));

    }

}

