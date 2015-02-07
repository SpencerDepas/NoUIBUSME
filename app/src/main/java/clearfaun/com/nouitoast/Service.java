package clearfaun.com.nouitoast;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by spencer on 2/1/2015.
 */
public class Service extends IntentService{


    static LocationManager locationManager;
    private static String provider;
    int accuracy;
    LocationListener locationListener;


    public Service() {
        super("MyIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent){
        // Handle events on worker thread here

        Log.i("MyActivity12", "onHandleIntent " );

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (locationManager != null) {

            Log.i("MyActivity12", "onHandleIntent locationManager != null" );


            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            locationManager.getBestProvider(criteria, true);

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            MyLocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);







            Log.i("MyActivity12", "before if getAccuracy <= " + (int)location.getAccuracy());


            if((int)location.getAccuracy() <= 10){

                MainActivity.latatude = location.getLatitude();
                MainActivity.longitude = location.getLongitude();


                GpsToAddress task = new GpsToAddress();
                task.execute();
                Log.i("MyActivity12", "in if GpsToAddress = " + accuracy);

            }else{
                Log.i("MyActivity12", "entering  untilAccurate" + accuracy);
                untilAccurate();
            }


            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


            /*accuracy = (int) location.getAccuracy();
            long time = System.currentTimeMillis();
            Log.i("MyActivity12", "before while accuracy loop — accuracy = " + accuracy);
            while (accuracy > 10) {


                accuracy = (int) location.getAccuracy();

                if (System.currentTimeMillis() >= time + 5000) {
                    break;
                }

            }*/
            //Log.i("MyActivity12", "after while accuracy loop — accuracy = " + accuracy);


        }








    }


    public void untilAccurate() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i("MyActivity12", "in  untilAccurate accuracy is " + (int)location.getAccuracy());
        MyLocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);



        if((int)location.getAccuracy() >= 15){
            Log.i("MyActivity12", "in  the if " + (int)location.getAccuracy());

            long time = System.currentTimeMillis();
            while(true){
                if (System.currentTimeMillis() >= time + 3000) {
                    break;
                }
            }

            untilAccurate();
        }else{

            MainActivity.latatude = location.getLatitude();
            MainActivity.longitude = location.getLongitude();


            GpsToAddress task = new GpsToAddress();
            task.execute();
            Log.i("MyActivity12", "in untilAccurate otw to  GpsToAddress" + (int)location.getAccuracy());

        }
        Log.i("MyActivity12", "in untilAccurate outside else" + (int)location.getAccuracy());

    }





}

