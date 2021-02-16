package com.example.timetracker;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserAppUsageHelper {
    private final Context context;
    public List<Object> app_usage = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

            String[] parts = us.getPackageName().split("\\.");
            if (currTotal > 0) {
                totalAppUsageTime += currTotal;
                allUsedApps.put(parts[parts.length - 1], currTotal);
                allApps.add(parts[parts.length - 1]); //need to find way that more apps are included
            }

        }

        List<String> top5Apps = getTop5AppsUsed(allUsedApps);
        app_usage.add(0, totalAppUsageTime);
        app_usage.add(1, top5Apps);

        System.out.println("TOTAL TIME:");
        System.out.println(totalAppUsageTime);
        System.out.println("TOP 5 APPS:");
        System.out.println(top5Apps);
        System.out.println("ALL APPS:");
        System.out.println(allApps);

        return allApps;
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


    // Discuss with group if should be added. Have a method... But would not adhere to our set
    // definition of categories, requires communicating with the app store, and parsing JSON
    // private void getCategoryBreakdown(HashMap<String,Double> allApps){ }
}
