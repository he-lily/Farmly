package com.example.timetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetLimits extends AppCompatActivity {
    Button generatingPlan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limits);

        /** Added button functionality here so we can get to the next activity. Change anything if you need to. **/
        generatingPlan = findViewById(R.id.save_limits);
        generatingPlan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(SetLimits.this, GeneratingPlan.class);
                startActivity(intent);
            }
        });
    }
}