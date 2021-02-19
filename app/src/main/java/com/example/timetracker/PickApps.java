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

        /** Added button functionality here so we can get to the next activity. Change anything if you need to. **/
        pickAppsButton = findViewById(R.id.save_apps);
        pickAppsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(PickApps.this, SetLimits.class);
                restrictedApps = myAdapter.checkedItems;
                System.out.println(restrictedApps);
                startActivity(intent);
            }
        });
    }
}