package com.example.timetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetLimits extends AppCompatActivity {
    Button generatingPlan;
    RecyclerView rv;
    List<String> mAppNames;
    List<Double> mAppTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limits);
        /** THIS IS THE CODE FOR DISPLAYING THE RECYCLER VIEW FOR THE SET LIMITS,
         * I HAVEN'T IMPORTED THE LIMITED APPS YET BUT I'LL DO THAT SOON.
         * THE ADAPTER AND LAYOUT FOR IT SHOULD BE GOOD THOUGH.
        rv = findViewById(R.id.recyclerView2);
        LimitedAppsAdapter ad = new LimitedAppsAdapter(this, mAppNames, mAppTimes);
        rv.setAdapter(ad);
        rv.setLayoutManager(new LinearLayoutManager(this));
         **/
        /** Added button functionality here so we can get to the next activity. Change anything if you need to. **/
            
        mAppNames = HoldUserInfo.getInstance().getUser_disliked_apps();
        int index = 0;
        HashMap<String,Double> app_usage = (HashMap<String,Double>)HoldUserInfo.getInstance().getUser_app_usage().get(4);
        for (String key : mAppNames) {
            try {
                mAppTimes.add(index, app_usage.get(key));
                index = index + 1;
            } catch (Exception e) {
                System.out.println("COULD NOT ADD: " + key);
            }
        }

        recyclerView = findViewById(R.id.recyclerView2);
        LimitedAppsAdapter ad = new LimitedAppsAdapter(this, mAppNames, mAppTimes);
        recyclerView.setAdapter(ad);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        generatingPlan = findViewById(R.id.save_limits);
        generatingPlan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(SetLimits.this, GeneratingPlan.class);
                startActivity(intent);
            }
        });
    }
}
