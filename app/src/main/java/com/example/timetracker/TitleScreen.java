package com.example.timetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleScreen extends AppCompatActivity {
    Button leaveSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
        leaveSignin = findViewById(R.id.getStarted);
        leaveSignin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(TitleScreen.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}