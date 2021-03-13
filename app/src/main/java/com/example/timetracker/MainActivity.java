package com.example.timetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    Button UserInfoButton;
    EditText UserName;
    EditText UserAge;
    EditText UserJob;
    EditText UserEmail;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gathers the user's app usage information and stores it in "app_usage"
        //      - App_usage is a list of size 2 (SUBJECT TO CHANGE)
        //      - Index 1: total time on apps
        //      - Index 2: list of top 5 apps used

        UserAppUsageHelper userAppHistory = new UserAppUsageHelper(MainActivity.this);

        UserInfoButton = findViewById(R.id.UserInfo_button);
        UserName = findViewById(R.id.name_prompt);
        UserAge = findViewById(R.id.age_prompt);
        UserJob = findViewById(R.id.job_prompt);
        UserEmail = findViewById(R.id.email_addr);

        UserInfoButton.setOnClickListener(view -> {
            userAppHistory.app_usage.add(2, userAppHistory.category_usage);
            Intent intent = new Intent(MainActivity.this,AppDescript1.class);

            HoldUserInfo.getInstance().setUser_name(UserName.getText().toString());
            HoldUserInfo.getInstance().setUser_email(UserEmail.getText().toString());
            HoldUserInfo.getInstance().setUser_job(UserJob.getText().toString());
            HoldUserInfo.getInstance().setUser_age(Integer.parseInt(UserAge.getText().toString()));
            HoldUserInfo.getInstance().setUser_app_usage(userAppHistory.app_usage);

            startActivity(intent);
        });
    }
}


