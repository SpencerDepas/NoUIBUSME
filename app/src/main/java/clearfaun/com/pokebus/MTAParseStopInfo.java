package clearfaun.com.pokebus;

import android.os.AsyncTask;
import android.util.Log;

import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by spencer on 1/28/2015.
 */
public class MTAParseStopInfo {





    public static class TechCrunchTask extends AsyncTask<Void, Void, Void> {

        int stopRadius;


        @Override
        protected void onPreExecute() {


            Log.i("MyActivity12", " MTAParseStopInfo = onPreExecute STP RADIUS: " + stopRadius);
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Void doInBackground(Void... params) {


            String stringLatatude = MainActivity.latatude + "";
            String stringlongitude = MainActivity.longitude + "";



            stringLatatude = stringLatatude.substring(0,9);
            stringlongitude = stringlongitude.substring(0,9);

            String downloadURL = "http://bustime.mta.info/api/where/stops-for-location.xml?key=" + MainActivity.API_KEY + "&radius=" + stopRadius + "&lat=" +
                    stringLatatude + "&lon=" + stringlongitude;



            try {

                Log.i("MyActivity12", " in try  " );
                URL url = new URL(downloadURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                processXML(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }



            return null;
        }

        Element rootElement;



        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.i("MyActivity12", "MTAParseStopInfo on post execute temp buscode " + tempBusCode
            + " radius : " + stopRadius);


            if( MainActivity.busInfo.getBusRadiusTaskNumber() == 0 && tempBusCode != 0) {
                Log.i("MyActivity12", "first setting of buscode "  + stopRadius + " " + tempBusCode);
                MainActivity.busInfo.busRadiusTaskNumber(stopRadius);
            }
            //129 is the highest radius
            if(stopRadius == 129 && tempBusCode == 0){
                Log.i("MyActivity12", "MTAParseStopInfo: No bus stops in range " );
                new ToastMessageTask().execute("No bus stops in range");
            }



            if(tempBusCode != 0 && stopRadius <= MainActivity.busInfo.getBusRadiusTaskNumber()) {

                Analytics.with(MainActivity.mContext).track("StopInfo", new Properties()
                        .putValue("stopRadius", stopRadius));

                Log.i("MyActivity12", "setting of busInfo tempBusCode: " + tempBusCode + "\n" + "tempBusName: " + tempBusName
                + " stopRadius: " + stopRadius);

                MainActivity.busInfo.busCode(tempBusCode);
                MainActivity.busInfo.busName(tempBusName);
                MainActivity.busInfo.busRadiusTaskNumber(stopRadius);

                //MainActivity.editTextTwo.setText(MainActivity.busInfo.getBusName() + ": " + MainActivity.busInfo.getBusCode() + " I am radius " + stopRadius);

                //MainActivity.editTextTwo.setText(MainActivity.busInfo.getBusName() + ": " + MainActivity.busInfo.getBusCode() + " I am radius " + MainActivity.busInfo.getBusRadiusTaskNumber());



                Log.i("MyActivity12", "MTAParseStopInfo: MTAParseDistance on pre execute " );
                MTAParseDistance.PlaceholderFragment.startTask();


            }



        }

        Node currentItem;
        NodeList itemChildren;
        Node currentChild;
        int tempBusCode;
        String tempBusName;



        public void processXML(InputStream inputStream) throws Exception{

            Log.i("MyActivity12", "in processXML ");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document xmlDocument = documentBuilder.parse(inputStream);
            rootElement = xmlDocument.getDocumentElement();


            NodeList itemsList = rootElement.getElementsByTagName("stop");
            currentItem = null;
            itemChildren = null;
            currentChild = null;

            Log.i("MyActivity12", "itemsList.getLength()  " + itemsList.getLength());
            for(int i = 0; i < itemsList.getLength(); i++){

                currentItem = itemsList.item(i);
                itemChildren = currentItem.getChildNodes();

                for(int j = 0; j < itemChildren.getLength(); j++){
                    currentChild = itemChildren.item(j);
                    if(currentChild.getNodeName().equalsIgnoreCase("code")){
                        tempBusCode = Integer.parseInt(currentChild.getTextContent());
                        Log.i("MyActivity12", "tempBusCode " + tempBusCode);
                        //busInfo.busCode(Integer.parseInt(currentChild.getTextContent()));
                    }

                }
            }


            itemsList = rootElement.getElementsByTagName("route");
            for(int i = 0; i < itemsList.getLength(); i++){
                currentItem = itemsList.item(i);
                itemChildren = currentItem.getChildNodes();

                for(int j = 0; j < itemChildren.getLength(); j++){
                    currentChild = itemChildren.item(j);
                    if(currentChild.getNodeName().equalsIgnoreCase("shortname")){
                        tempBusName = currentChild.getTextContent();
                        Log.i("MyActivity12", "tempBusCode " + tempBusName);
                        //busInfo.busName(currentChild.getTextContent());
                    }
                }
            }



        }



    }








}
