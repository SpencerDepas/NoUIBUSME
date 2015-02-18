package clearfaun.com.pokebus;

/**
 * Created by spencer on 1/28/2015.
 */
public class BusInfo {


    public int stopCode = 0;
    public String busName = "";
    public String distance = "No buses on route";
    public int radiusTaskNumber = 0;
    //constructor


    public void busCode(int codeforStop) {
        stopCode = codeforStop;
    }

    public void busName(String nameOfBus) {
        busName = nameOfBus;
    }

    public void busDistance(String busDistance) {
        distance = busDistance;
    }

    public void busRadiusTaskNumber(int TaskNumber) {
        radiusTaskNumber = TaskNumber;
    }





    public int getBusCode(){
        return stopCode;
    }

    public int getBusRadiusTaskNumber(){
        return radiusTaskNumber;
    }

    public String getBusName(){
        return busName;
    }

    public String getDistance() {
        return distance;
    }





}
