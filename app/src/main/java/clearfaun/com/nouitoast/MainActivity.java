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
import android.util.Log;
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



    final static public String API_KEY = "05a5c2c8-432a-47bd-8f50-ece9382b4b28";


    static String testLat = "40.6455520";
    static String testLng = "-73.9829084";

    public static BusInfo busInfo = new BusInfo();
    static Geocoder geocoder;
    static Context mContext;


    static double latatude;
    static double longitude;
    static List<Address> addresses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();


        Log.i("MyActivity12", "onCreate" );



    }




    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

        LocationManager lService = (LocationManager) getSystemService(LOCATION_SERVICE);
        final boolean enabled = lService.isProviderEnabled(LocationManager.GPS_PROVIDER);


        Log.i("MyActivity12", "onResume" );

        if (!enabled) {
            Log.i("MyActivity12", "!enabled" );
            toaster("Please enable GPS");

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
            Log.i("MyActivity12", "Loading" );
            toasterShort("Loading");
            Intent service = new Intent(this, Service.class);
            startService(service);


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
