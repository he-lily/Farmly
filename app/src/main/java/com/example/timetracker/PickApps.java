package com.example.timetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import java.util.List;

public class PickApps extends AppCompatActivity {
    List<String> allApps;
    RecyclerView recyclerView;
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


    }
}