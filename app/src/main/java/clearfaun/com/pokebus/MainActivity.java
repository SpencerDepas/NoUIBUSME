package clearfaun.com.pokebus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;

import java.util.List;


public class MainActivity extends Activity {



    final static public String API_KEY = "05a5c2c8-432a-47bd-8f50-ece9382b4b28";


    static String testLat = "40.6455520";
    static String testLng = "-73.9829084";

    public static BusInfo busInfo = new BusInfo();
    static Geocoder geocoder;
    static Context mContext;


    static double latatude;
    static double longitude;
    static List<Address> addresses;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        Log.i("MyActivity12", "onCreate" );



        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = sharedpreferences.edit();

        int firstBoot = sharedpreferences.getInt("first_boot", 0);

        if(firstBoot == 410){
            Analytics.with(this).track("App open", new Properties());
        }else{
            editor.putInt("first_boot", 410);
            editor.apply();
            Analytics.with(this).track("App open first time", new Properties());
        }

    }




    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

        LocationManager lService = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = lService.isProviderEnabled(LocationManager.GPS_PROVIDER);



        Log.i("MyActivity12", "onResume" );

        if (!enabled) {
            Log.i("MyActivity12", "!enabled" );
            toaster("Please enable GPS");
            Analytics.with(this).track("Please enable GPS", new Properties());
            long time = System.currentTimeMillis();
            while(true){
                if (System.currentTimeMillis() >= time + 1000) {
                    break;
                }
            }
            //put gps on
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);


        }else {

            long timeStamp = System.currentTimeMillis();
            long limitPresses = sharedpreferences.getLong("limit_presses", 0);

            //only press ever 3 seconds
            if(limitPresses == 0 || limitPresses + 3000 <= timeStamp) {


                editor.putLong("limit_presses", timeStamp);
                editor.apply();
                Log.i("MyActivity12", "Loading");
                toasterShort("Loading");
                Intent service = new Intent(this, Service.class);
                startService(service);

            }


        }

        finish();
    }




    static void toaster(String string){
        Toast toast = Toast.makeText(mContext, string, Toast.LENGTH_LONG);
        toast.show();
    }

    static void toasterShort(String string){
        Toast toast = Toast.makeText(mContext, string, Toast.LENGTH_SHORT);
        toast.show();
    }


}
