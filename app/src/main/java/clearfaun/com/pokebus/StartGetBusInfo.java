package clearfaun.com.pokebus;

/**
 * Created by spencer on 2/19/2015.
 */
public class StartGetBusInfo {


    public static void startMTAParseStopInfo(){


        MTAParseStopInfo.TechCrunchTask downloadTaskOne = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskOne.stopRadius = 4;
        downloadTaskOne.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskTwo = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskTwo.stopRadius = 9;
        downloadTaskTwo.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskThree = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskThree.stopRadius = 20;
        downloadTaskThree.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskFour = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskFour.stopRadius = 31;
        downloadTaskFour.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskFive = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskFive.stopRadius = 42;
        downloadTaskFive.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskSix = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskSix.stopRadius = 53;
        downloadTaskSix.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskSeven = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskSeven.stopRadius = 69;
        downloadTaskSeven.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskEight = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskEight.stopRadius = 99;
        downloadTaskEight.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskNine = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskNine.stopRadius = 124;
        downloadTaskNine.execute();

        MTAParseStopInfo.TechCrunchTask downloadTaskTen = new MTAParseStopInfo.TechCrunchTask();
        downloadTaskTen.stopRadius = 129;
        downloadTaskTen.execute();













    }





}
