package com.example.timetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button UserInfoButton;
    EditText UserName;
    EditText UserAge;
    EditText UserJob;
    EditText UserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserInfoButton = findViewById(R.id.UserInfo_button);
        UserName = findViewById(R.id.name_prompt);
        UserAge = findViewById(R.id.age_prompt);
        UserJob = findViewById(R.id.job_prompt);
        UserEmail = findViewById(R.id.email_addr);
        UserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //save info to UserDB and move to next activity
             public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,AppDescript1.class);
                UserDataBaseHelper userDB = new UserDataBaseHelper(MainActivity.this);
                userDB.addUser(UserEmail.getText().toString(),
                        UserName.getText().toString(),
                        UserJob.getText().toString(),
                        Integer.parseInt(UserAge.getText().toString()));
                startActivity(intent);
            }
        });

    }
}