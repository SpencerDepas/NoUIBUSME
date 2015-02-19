package clearfaun.com.pokebus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;

import java.util.List;


public class MainActivity extends Activity {



    final static public String API_KEY = "05a5c2c8-432a-47bd-8f50-ece9382b4b28";

    public static final int REQUEST_CODE = 1;
    static String testLat = "40.6455520";
    static String testLng = "-73.9829084";

    public static BusInfo busInfo = new BusInfo();

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


    public static final int GPS_EVENT_STARTED = 1;

    @Override
    public void onResume(){
        super.onResume();

        Log.i("MyActivity12", "onResume");



        LocationManager lService = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = lService.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledAirplaneMode = isAirplaneModeOn(mContext);

        if(enabledAirplaneMode){

            toaster("Please turn off Airplane mode");
            Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
            startActivity(intent);



        }else  if (!enabledGPS) {

            Log.i("MyActivity12", "!enabled");
            toaster("Please enable GPS");
            Analytics.with(this).track("Please enable GPS", new Properties());
            long time = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() >= time + 1000) {
                    break;
                }
            }

            //put gps on
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);

            lService.addGpsStatusListener(new android.location.GpsStatus.Listener() {
                public void onGpsStatusChanged(int event) {

                    //closes gps intent, back to home screen
                    if(event == 1) {

                        finish();
                        Log.i("MyActivity12", "!onGpsStatusChanged");
                    }

                }
            });


        } else {

            long timeStamp = System.currentTimeMillis();
            long limitPresses = sharedpreferences.getLong("limit_presses", 0);

            //only press ever 3 seconds
            if (limitPresses == 0 || limitPresses + 3000 <= timeStamp) {


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


    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

}
