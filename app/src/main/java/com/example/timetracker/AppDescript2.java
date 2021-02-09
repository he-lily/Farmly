package com.example.timetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppDescript2 extends AppCompatActivity {
    Button descript2Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_descript2);
        descript2Button = findViewById(R.id.descript2_button);
        descript2Button.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view){
            Intent intent = new Intent(AppDescript2.this,SelectCategories.class);
            startActivity(intent);
        }
        });
    }
}