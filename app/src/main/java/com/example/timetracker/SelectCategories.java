package com.example.timetracker;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


import java.util.ArrayList;
import java.util.List;

public class SelectCategories extends AppCompatActivity {
    CheckBox fitness,edu,finance,music,food,gaming,sports,housing,
             dating,social,parenting,shopping,beauty,photography,art,
            medical,productivity,news,lifestyle,entertainment,travel;
    Button button;
    List<String> selected_categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_categories);
        button = (Button) findViewById(R.id.save_categories);
        fitness = (CheckBox) findViewById(R.id.fitness_cb);
        edu = (CheckBox) findViewById(R.id.edu_cb);
        finance = (CheckBox) findViewById(R.id.finance_cb);
        music = (CheckBox) findViewById(R.id.music_cb);
        food = (CheckBox) findViewById(R.id.food_cb);
        gaming = (CheckBox) findViewById(R.id.gaming_cb);
        sports = (CheckBox) findViewById(R.id.sports_cb);
        housing = (CheckBox) findViewById(R.id.house_cb);
        dating = (CheckBox) findViewById(R.id.dating_cb);
        social = (CheckBox) findViewById(R.id.social_cb);
        parenting = (CheckBox) findViewById(R.id.parenting_cb);
        shopping = (CheckBox) findViewById(R.id.shopping_cb);
        beauty = (CheckBox) findViewById(R.id.beauty_cb);
        photography = (CheckBox) findViewById(R.id.photo_cb);
        art = (CheckBox) findViewById(R.id.art_cb);
        medical = (CheckBox) findViewById(R.id.medical_cb);
        productivity = (CheckBox) findViewById(R.id.productivity_cb);
        news = (CheckBox) findViewById(R.id.news_cb2);
        lifestyle = (CheckBox) findViewById(R.id.lifestyle_cb);
        entertainment = (CheckBox) findViewById(R.id.entertain_cb);
        travel = (CheckBox) findViewById(R.id.travel_cb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //save info to UserDB and move to next activity
            public void onClick(View view){
                Intent intent = new Intent(SelectCategories.this,PickApps.class);
                //UserDataBaseHelper userDB = new UserDataBaseHelper(SelectCategories.this);
                //userDB.addPreferredCategories(selected_categories.toString());
                HoldUserInfo.getInstance().setUser_preferred_categories(selected_categories);
                startActivity(intent);
            }
        });
    }


    public void onCheckboxClicked(View view) {
        selected_categories = new ArrayList<String>();
        if(fitness.isChecked()){
            selected_categories.add("\"HEALTH_AND_FITNESS\"");
        }
        if(edu.isChecked()){
            selected_categories.add("\"EDUCATION\"");
        }
        if(finance.isChecked()){
            selected_categories.add("\"FINANCE\"");
        }
        if(music.isChecked()){
            selected_categories.add("\"MUSIC_AND_AUDIO\"");
        }
        if(food.isChecked()){
            selected_categories.add("\"FOOD_AND_DRINK\"");
        }
        if(gaming.isChecked()){
            selected_categories.add("\"GAME\"");
        }
        if(sports.isChecked()){
            selected_categories.add("\"SPORTS\"");
        }
        if(housing.isChecked()){
            selected_categories.add("\"HOUSE_AND_HOME\"");
        }
        if(dating.isChecked()){
            selected_categories.add("\"DATING\"");
        }
        if(social.isChecked()){
            selected_categories.add("\"SOCIAL\"");
        }
        if(parenting.isChecked()){
            selected_categories.add("\"PARENTING\"");
        }
        if(shopping.isChecked()){
            selected_categories.add("\"SHOPPING\"");
        }
        if(beauty.isChecked()){
            selected_categories.add("\"BEAUTY\"");
        }
        if(photography.isChecked()){
            selected_categories.add("\"PHOTOGRAPHY\"");
        }
        if(art.isChecked()){
            selected_categories.add("\"ART_AND_DESIGN\"");
        }
        if(medical.isChecked()){
            selected_categories.add("\"MEDICAL\"");
        }
        if(productivity.isChecked()){
            selected_categories.add("\"PRODUCTIVITY\"");
        }
        if(news.isChecked()){
            selected_categories.add("\"NEWS_AND_MAGAZINES\"");
        }
        if(lifestyle.isChecked()){
            selected_categories.add("\"LIFESTYLE\"");
        }
        if(entertainment.isChecked()){
            selected_categories.add("\"ENTERTAINMENT\"");
        }
        if(travel.isChecked()){
            selected_categories.add("\"TRAVEL_AND_LOCAL\"");
        }
    }
}
