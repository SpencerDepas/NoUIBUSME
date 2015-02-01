package clearfaun.com.nouitoast;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by spencer on 2/1/2015.
 */
class GpsToAddress extends AsyncTask<Location, Void, String> {



    @Override
    protected String doInBackground(Location... params) {

        MainActivity.geocoder = new Geocoder(MainActivity.mContext, Locale.ENGLISH);
        String result = null;
        int bob = 0;

        Log.i("MyActivity12", " GpsToAddress = " );


        try
        {
            bob++;
            MainActivity.addresses = MainActivity.geocoder.getFromLocation(MainActivity.latatude, MainActivity.longitude, 1);




            if (MainActivity.addresses != null && MainActivity.addresses.size() > 0 ){
                bob++;

                Address returnAddress = MainActivity.addresses.get(0);
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
        if(String.valueOf(MainActivity.longitude).length() > 8){

            Log.i("MyActivity12", " GpsToAddress = onPostExecute" + MainActivity.longitude);

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