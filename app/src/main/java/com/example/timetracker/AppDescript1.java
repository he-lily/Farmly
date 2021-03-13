package com.example.timetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppDescript1 extends AppCompatActivity {
    Button descript1Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_descript1);
        descript1Button = findViewById(R.id.descript1_button);
        //when the "NEXT" button is clicked, start the next Intent, which is 2nd onboarding slide 
        //displaying "help make your screen productive"
        descript1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(AppDescript1.this,AppDescript2.class);
                startActivity(intent);
            }
        });
    }


}
