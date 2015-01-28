package clearfaun.com.nouitoast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {




    private static LocationManager locationManager;
    private static String provider;
    static EditText editText;
    static EditText editTextTwo;
    static EditText editTextThree;

    final static public String API_KEY = "05a5c2c8-432a-47bd-8f50-ece9382b4b28";


    static String testLat = "40.6455520";
    static String testLng = "-73.9829084";


    static double latatude;
    static double longitude;
    static List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();


        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        final boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);

        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);



        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }


        int accuracy = (int) location.getAccuracy();

        while (accuracy > 10) {

            //do nothing!
            //need to add a case where its not acurate.
        }


        // Initialize the location fields
        if (location != null) {

            onLocationChanged(location);
        }


        GpsToAddress task = new GpsToAddress();
        task.execute();


    }







    public static BusInfo busInfo = new BusInfo();
    static Geocoder geocoder;

    private static class GpsToAddress extends AsyncTask<Location, Void, String> {



        @Override
        protected String doInBackground(Location... params) {

            geocoder = new Geocoder(mContext, Locale.ENGLISH);
            String result = null;
            int bob = 0;



            try
            {
                bob++;
                addresses = geocoder.getFromLocation(latatude, longitude, 1);
                StringBuilder str = new StringBuilder();



                if (addresses != null && addresses.size() > 0 ){
                    bob++;

                    Address returnAddress = addresses.get(0);
                    returnAddress.toString();

                    String localityString = returnAddress.getAddressLine(0);
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    return localityString;
                }
                else
                {
                    return "Cock";
                }
            } catch (IOException e) {
                bob+= 55;
            }



            return bob + "";
        }




        @Override
        protected void onPostExecute(String address) {

            //toaster(address + "");
            //editText.setText(address);
            if(String.valueOf(longitude).length() > 8){



                MTAParseStopInfo.TechCrunchTask downloadTaskOne = new MTAParseStopInfo.TechCrunchTask();
                downloadTaskOne.stopRadius = 10;
                downloadTaskOne.execute();

                MTAParseStopInfo.TechCrunchTask downloadTaskTwo = new MTAParseStopInfo.TechCrunchTask();
                downloadTaskTwo.stopRadius = 25;
                downloadTaskTwo.execute();

                MTAParseStopInfo.TechCrunchTask downloadTaskThree = new MTAParseStopInfo.TechCrunchTask();
                downloadTaskThree.stopRadius = 50;
                downloadTaskThree.execute();

                MTAParseStopInfo.TechCrunchTask downloadTaskFour = new MTAParseStopInfo.TechCrunchTask();
                downloadTaskFour.stopRadius = 120;
                downloadTaskFour.execute();





                //this is now in stop info as it does not update on second press here
                //MTAParseDistance.PlaceholderFragment.startTask();
                //test comment

            }

        }

    }

    public static void onLocationChanged(Location location) {
        latatude =  location.getLatitude();
        longitude =  location.getLongitude();
        //editText.setText(String.valueOf(latatude) + "\n" + String.valueOf(longitude));


    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        finish();
    }

    static Context mContext;




    static void toaster(String string){


        Toast toast = Toast.makeText(mContext, string, Toast.LENGTH_SHORT);
        toast.show();
    }



}
