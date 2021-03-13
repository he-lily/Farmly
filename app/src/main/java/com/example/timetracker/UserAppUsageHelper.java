package com.example.timetracker;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class UserAppUsageHelper {
    private final Context context;
    public List<Object> app_usage = new ArrayList<>();
    public final static String GOOGLE_URL = "https://play.google.com/store/apps/details?id=";
    Map<String, Double> category_usage = createMap();
    Map<String, String> allApps_Map = new HashMap<>();

    
    private static Map<String, Double> createMap() {
        Map<String,Double> myMap = new HashMap<>();

        myMap.put("ART_AND_DESIGN", 0.0);
        myMap.put("BEAUTY", 0.0);
        myMap.put("DATING", 0.0);
        myMap.put("EDUCATION", 0.0);
        myMap.put("ENTERTAINMENT", 0.0);
        myMap.put("FINANCE", 0.0);
        myMap.put("FOOD_AND_DRINK", 0.0);
        myMap.put("GAME", 0.0);
        myMap.put("HEALTH_AND_FITNESS", 0.0);
        myMap.put("HOUSE_AND_HOME", 0.0);
        myMap.put("LIFESTYLE", 0.0);
        myMap.put("MEDICAL", 0.0);
        myMap.put("MUSIC_AND_AUDIO", 0.0);
        myMap.put("NEWS_AND_MAGAZINES", 0.0);
        myMap.put("PARENTING", 0.0);
        myMap.put("PHOTOGRAPHY", 0.0);
        myMap.put("PRODUCTIVITY", 0.0);
        myMap.put("SHOPPING", 0.0);
        myMap.put("SOCIAL", 0.0);
        myMap.put("SPORTS", 0.0);
        myMap.put("TRAVEL_AND_LOCAL", 0.0);

        return myMap;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public UserAppUsageHelper(@Nullable Context context){
        this.context = context;
        if (!hasPermissionBeenGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
            assert context != null;
            context.startActivity(intent);
        }
        gatherAppUsageHistory();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean hasPermissionBeenGranted() {
        AppOpsManager appOps = (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> gatherAppUsageHistory(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        UsageStatsManager mUsageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> queryUsageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, cal.getTimeInMillis(), System.currentTimeMillis());

        HashMap<String,Double> allUsedApps = new HashMap<>();
        List<String> allApps =  new ArrayList<>();
        double totalAppUsageTime = 0.0;
        for (UsageStats us : queryUsageStats) {

            double currTotal = us.getTotalTimeInForeground() / 1000.0;

            if (currTotal > 0) {
                String query_url = GOOGLE_URL + us.getPackageName();
                getCategory(query_url, currTotal);
                String[] parts = us.getPackageName().split("\\.");

                totalAppUsageTime += currTotal;
                allUsedApps.put(parts[parts.length - 1], currTotal);
                allApps.add(parts[parts.length - 1]); //need to find way that more apps are included
            }

        }

        System.out.println(allUsedApps);

        List<String> top5Apps = getTop5AppsUsed(allUsedApps);
        app_usage.add(0, totalAppUsageTime);
        app_usage.add(1, top5Apps);
        app_usage.add(2, category_usage);
        app_usage.add(3, allUsedApps);


        System.out.println("TOTAL TIME:");
        System.out.println(totalAppUsageTime);
        System.out.println("TOP 5 APPS:");
        System.out.println(top5Apps);
        System.out.println("ALL APPS:");
        System.out.println(allApps);
        System.out.println("CATEGORY TIME:");
        System.out.println(category_usage);

        return allApps;
    }
    
    private class CategoriesTask extends AsyncTask<Map<String,Double>, Void, Map<String,Double>> {
        @Override
        protected Map<String,Double> doInBackground(Map<String,Double> ... url) {
           try {
                Document doc = Jsoup.connect((String)url[0].keySet().toArray()[0]).get();
                Elements scriptElements = doc.getElementsByTag("script");
                System.out.println("INSIDE CATEGORY TASK:) " );
                for (Element element : scriptElements) {
                    if (element.data().contains("applicationCategory")) {
                        String category = element.data().substring(element.data().indexOf("applicationCategory"));
                        String app_logo = element.data().substring(element.data().indexOf("\"image\""));
                        String app_name = element.data().substring(element.data().indexOf("\"name\""));
                        app_name = app_name.substring(8,app_name.indexOf(",")-1);
                        app_logo = app_logo.substring(9, app_logo.indexOf(",")-1);
                        category = category.substring(22, category.indexOf(",")-1);
                        allApps_Map.put(app_name.toLowerCase(), app_logo);
                        if(!allApps_Map.isEmpty()) {
                            HoldUserInfo.getInstance().setUser_all_apps(allApps_Map);
                            System.out.println("DICT: " + allApps_Map);

                        }
                        if (category.contains("GAME"))
                            category = "GAME";

                        if (!category_usage.containsKey(category))
                            return null;
                        else {
                            Map<String, Double> catSet = new HashMap<>();
                            catSet.put(category, (Double) url[0].values().toArray()[0]);
                            return catSet;
                        }
                    }
                }
           }
           catch (MalformedURLException e) {System.out.println(" MalformedURLException " ); }
           catch(HttpStatusException e1) {System.out.println(" HttpStatusException " ); }
           catch(UnsupportedMimeTypeException e2) {System.out.println(" UnsupportedMimeTypeException " ); }
           catch(SocketTimeoutException e3) {System.out.println(" SocketTimeoutException " ); }
           catch(IOException e4) {System.out.println(" IOException " ); }

           return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Map<String,Double> result) {
            if (result != null){
                for (Map.Entry<String,Double> entry : result.entrySet()) {
                    category_usage.replace(entry.getKey(), category_usage.get(entry.getKey()) + entry.getValue());
                }
                System.out.println(category_usage);
            }
        }
    }

    
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getCategory(String query_url, Double timeTotal) {
        Map<String,Double> urlSet = new HashMap<>();
        urlSet.put(query_url, timeTotal);
        CategoriesTask task = new CategoriesTask();

        System.out.println("DICT: " + HoldUserInfo.getInstance().getUser_all_apps());
        task.execute(urlSet);

        return "";
    }



    private List<String> getTop5AppsUsed(HashMap<String,Double> allApps){
        List<Map.Entry<String,Double>> appList = new LinkedList<>(allApps.entrySet());
        Collections.sort(appList, (Comparator)(a,b) -> ((Comparable)((Map.Entry)(b)).getValue()).compareTo(((Map.Entry)(a)).getValue()));

        int limit = 0;
        List<String> top5 =  new ArrayList<>();
        for (Map.Entry<String,Double> app : appList) {
            if (limit < 5){
                top5.add(app.getKey());
                limit += 1;
            } else {
                break;
            }
        }

        return top5;
    }
}
