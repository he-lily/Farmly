package com.example.timetracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "what should this say" ;
    Button UserInfoButton;
    EditText UserName;
    EditText UserAge;
    EditText UserJob;
    EditText UserEmail;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserInfoButton = findViewById(R.id.UserInfo_button);
        UserName = findViewById(R.id.name_prompt);
        UserAge = findViewById(R.id.age_prompt);
        UserJob = findViewById(R.id.job_prompt);
        UserEmail = findViewById(R.id.email_addr);
        UserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //save info to UserDB and move to next activity
             public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,AppDescript1.class);
                UserDataBaseHelper userDB = new UserDataBaseHelper(MainActivity.this);
                userDB.addUser(UserEmail.getText().toString(),
                        UserName.getText().toString(),
                        UserJob.getText().toString(),
                        Integer.parseInt(UserAge.getText().toString()));
                startActivity(intent);
            }
        });
        Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
        checkIfAlreadyhavePermission();
        startActivity(intent);

// Show stats

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);

        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        List<UsageStats> queryUsageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, cal.getTimeInMillis(), System.currentTimeMillis());
        //Map<String, UsageStats> aggregatedStatsMap= mUsageStatsManager.queryAndAggregateUsageStats(cal.getTimeInMillis(), System.currentTimeMillis());
        System.out.println("USAGE STATS: \n\n");
        System.out.println(queryUsageStats);
        System.out.println("PRINTING: \n\n");
        for (UsageStats us : queryUsageStats) {
            System.out.println(us.getPackageName() + " = " + us.getTotalTimeInForeground());
        }


    }

    //this does not work - trying to make it so we only prompt user to change permission when it is off
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            System.out.println("TRUE!!!!!!!!!!!!!!!! \n\n");
            return true;
        } else {
            System.out.println("FALSEEEEEEEE!!!!!!!!!!!!! \n\n");
            return false;
        }
    }


}