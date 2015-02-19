package clearfaun.com.pokebus;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;


import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;




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



            Log.i("MyService", "Service onHandleIntent " );

            locationManager = (LocationManager) MainActivity.mContext.getSystemService(Context.LOCATION_SERVICE);


            if (locationManager != null) {

                Log.i("MyService", "onHandleIntent locationManager != null");




                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);

                locationManager.getBestProvider(criteria, true);

                LocationListener locationListener = new MyLocationListener();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    if (location.hasAccuracy()) {



                        Log.i("MyService", "before if getAccuracy test:  " + (int) location.getAccuracy());

                        //this sets first test for desired accuracy
                        if ((int) location.getAccuracy() <= 10 && (int) location.getAccuracy() != 0) {

                            Analytics.with(this).track("Accuracy", new Properties()
                                    .putValue("Accuracy", (int) location.getAccuracy() + ""));

                            MainActivity.latatude = location.getLatitude();
                            MainActivity.longitude = location.getLongitude();


                            GpsToAddress task = new GpsToAddress();
                            task.execute();
                            Log.i("MyService", "in if GpsToAddress <= " + (int) location.getAccuracy());

                        } else {
                            Log.i("MyService", "entering  untilAccurate " + (int) location.getAccuracy());
                            untilAccurate();
                        }


                    }else {
                        Log.i("MyService", "!location.hasAccuracy()");
                    }

                }else{

                    new ToastMessageTask().execute("Location not available");
                    Log.i("MyService", "location == null " );
                }
            }



        }

        int trackUntilAccurateLoops = 0;
        public void untilAccurate() {

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i("MyServiceUntilAccurate", "in  untilAccurate accuracy is :" + (int)location.getAccuracy() + "  trackUntilAccurateLoops: " + trackUntilAccurateLoops);
            MyLocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


            if(trackUntilAccurateLoops < 3) {
                Log.i("MyServiceUntilAccurate", "in  trackUntilAccurateLoops  :  trackUntilAccurateLoops: " + trackUntilAccurateLoops );
                if ((int) location.getAccuracy() >= 15 && (int) location.getAccuracy() != 0) {





                    long time = System.currentTimeMillis();
                    Log.i("MyServiceUntilAccurate", "location.getAccuracy() >= 15  Accuracy: " + (int) location.getAccuracy());
                    while (true) {
                        if (System.currentTimeMillis() >= time + 3000) {
                            break;
                        }
                    }
                    Log.i("MyServiceUntilAccurate", "after while loop: " + trackUntilAccurateLoops);
                    trackUntilAccurateLoops++;
                    untilAccurate();
                } else {

                    if (trackUntilAccurateLoops != 0) {
                        Analytics.with(this).track("untilAccurate", new Properties()
                                .putValue("UntilAccurateLoops", trackUntilAccurateLoops + ""));
                    }

                    Analytics.with(this).track("untilAccurate", new Properties()
                            .putValue("Accuracy", (int) location.getAccuracy() + ""));
                    Log.i("MyServiceUntilAccurate", "in untilAccurate otw to  GpsToAddress" + (int) location.getAccuracy());
                    MainActivity.latatude = location.getLatitude();
                    MainActivity.longitude = location.getLongitude();

                    trackUntilAccurateLoops = 0;
                    GpsToAddress task = new GpsToAddress();
                    task.execute();


                }

            }else{
                if (trackUntilAccurateLoops != 0) {
                    Analytics.with(this).track("untilAccurate", new Properties()
                            .putValue("UntilAccurateLoops", trackUntilAccurateLoops + ""));
                }
                Log.i("MyServiceUntilAccurate", "Accurate GPS reading can not be given "  + (int) location.getAccuracy());
                new ToastMessageTask().execute("Accurate GPS reading can not be given");
            }

        }





    }