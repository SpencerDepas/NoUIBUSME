package clearfaun.com.nouitoast;

import android.os.AsyncTask;

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
public class MTAParseDistance {


    public static class PlaceholderFragment  {

        static TechCrunchTask downloadTask;
        public PlaceholderFragment() {
        }



        public static void startTask(){

            if(downloadTask != null){
                downloadTask.cancel(true);
            }else{
                downloadTask = new TechCrunchTask();
                downloadTask.execute();
            }
        }

    }

    public static class TechCrunchTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Void doInBackground(Void... params) {
            //do your work here

            String stopCode = MainActivity.busInfo.getBusCode() + "";


            String downloadURL = "http://bustime.mta.info/api/siri/stop-monitoring.xml?key=05a5c2c8-432a-47bd-8f50-ece9382b4b28&MonitoringRef=MTA_" + stopCode + "&MaximumStopVisits=1";
            try {
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

            //editText.setText(rootElement.getTagName());
            //editText.setText(currentItem.getNodeName() + ": " + currentChild.getTextContent());

            //MainActivity.editTextThree.setText(MainActivity.busInfo.getDistance());


            MainActivity.toaster( MainActivity.busInfo.getBusCode() + "\n" + MainActivity.busInfo.busName + ": " + MainActivity.busInfo.getDistance());
            //ToastMe(rootElement.toString());
            // do something with data here-display it or send to mainactivity

            //MainActivity.killApp();

        }

        Node currentItem;
        NodeList itemChildren;
        Node currentChild;


        public void processXML(InputStream inputStream) throws Exception{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document xmlDocument = documentBuilder.parse(inputStream);
            rootElement = xmlDocument.getDocumentElement();





            NodeList itemsList = rootElement.getElementsByTagName("Distances");
            currentItem = null;
            itemChildren = null;
            currentChild = null;
            for(int i = 0; i < itemsList.getLength(); i++){

                currentItem = itemsList.item(i);
                itemChildren = currentItem.getChildNodes();

                for(int j = 0; j < itemChildren.getLength(); j++){
                    currentChild = itemChildren.item(j);
                    if(currentChild.getNodeName().equalsIgnoreCase("presentableDistance")){
                        MainActivity.busInfo.busDistance(currentChild.getTextContent());
                    }

                }
            }


        }



    }











}
