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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
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
        //save info to UserDB and move to next activity
        UserInfoButton.setOnClickListener(view -> {
           Intent intent = new Intent(MainActivity.this,AppDescript1.class);
           UserDataBaseHelper userDB = new UserDataBaseHelper(MainActivity.this);
           userDB.addUser(UserEmail.getText().toString(),
                   UserName.getText().toString(),
                   UserJob.getText().toString(),
                   Integer.parseInt(UserAge.getText().toString()), userAppHistory.app_usage.toString());
           startActivity(intent);
       });
    }
}