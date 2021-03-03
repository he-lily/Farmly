package com.example.timetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Arrays;
import java.util.List;

public class PickApps extends AppCompatActivity {
    List<String> allApps;
    RecyclerView recyclerView;
    Button pickAppsButton;
    List<String> restrictedApps;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_apps);

        UserAppUsageHelper userAppHistory = new UserAppUsageHelper(PickApps.this);
        recyclerView = findViewById(R.id.recyclerView);
        allApps = userAppHistory.gatherAppUsageHistory();

        MyAdapter myAdapter = new MyAdapter(this,allApps);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pickAppsButton = findViewById(R.id.save_apps);
        pickAppsButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View view){
                Intent intent = new Intent(PickApps.this, SetLimits.class);
                restrictedApps = myAdapter.checkedItems;
                HoldUserInfo.getInstance().setUser_disliked_apps(restrictedApps);

                AppsDataBaseHelper db_helper = new AppsDataBaseHelper(PickApps.this,null,null,2);
                List<String> rec_apps = db_helper.loadHandler();
                //List<String> messages = Arrays.asList("Hello", "World!", "How", "Are", "You");
                HoldUserInfo.getInstance().setUser_has_been_recommended(rec_apps);
                UserDataBaseHelper userDB = new UserDataBaseHelper(PickApps.this);
                userDB.addUser(HoldUserInfo.getInstance().getUser_email(),
                               HoldUserInfo.getInstance().getUser_name(),
                               HoldUserInfo.getInstance().getUser_job(),
                               HoldUserInfo.getInstance().getUser_age(),
                               HoldUserInfo.getInstance().getUser_app_usage().toString(),
                               HoldUserInfo.getInstance().getUser_has_been_recommended().toString(),
                               HoldUserInfo.getInstance().getUser_preferred_categories().toString(),
                               HoldUserInfo.getInstance().getUser_disliked_apps().toString());

                startActivity(intent);
            }
        });
    }
}