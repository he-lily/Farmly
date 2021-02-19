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
                System.out.println(selected_categories); //list of their perferred apps
                startActivity(intent);
            }
        });
    }


    public void onCheckboxClicked(View view) {
        selected_categories = new ArrayList<String>();
        if(fitness.isChecked()){
            selected_categories.add(fitness.getText().toString());
            System.out.println("yay it's working since I selected it");
        }
        if(edu.isChecked()){
            selected_categories.add(edu.getText().toString());
            System.out.println("edu yay it's working since I selected it");
        }
        if(finance.isChecked()){
            selected_categories.add(finance.getText().toString());
            System.out.println("finance yay it's working since I selected it");
        }
        if(music.isChecked()){
            selected_categories.add(music.getText().toString());
            System.out.println("music yay it's working since I selected it");
        }
        if(food.isChecked()){
            selected_categories.add(food.getText().toString());
            System.out.println("yay it's working since I selected it");
        }
        if(gaming.isChecked()){
            selected_categories.add(gaming.getText().toString());
            System.out.println("yay it's working since I selected it");
        }
        if(sports.isChecked()){
            selected_categories.add(sports.getText().toString());
            System.out.println("sports yay it's working since I selected it");
        }
        if(housing.isChecked()){
            selected_categories.add(housing.getText().toString());
            System.out.println("housing yay it's working since I selected it");
        }
        if(dating.isChecked()){
            selected_categories.add(dating.getText().toString());
            System.out.println("dating yay it's working since I selected it");
        }
        if(social.isChecked()){
            selected_categories.add(social.getText().toString());
            System.out.println("social yay it's working since I selected it");
        }
        if(parenting.isChecked()){
            selected_categories.add(parenting.getText().toString());
        }
        if(shopping.isChecked()){
            selected_categories.add(shopping.getText().toString());
        }
        if(beauty.isChecked()){
            selected_categories.add(beauty.getText().toString());
        }
        if(photography.isChecked()){
            selected_categories.add(photography.getText().toString());
        }
        if(art.isChecked()){
            selected_categories.add(art.getText().toString());
        }
        if(medical.isChecked()){
            selected_categories.add(medical.getText().toString());
        }
        if(productivity.isChecked()){
            selected_categories.add(productivity.getText().toString());
        }
        if(news.isChecked()){
            selected_categories.add(news.getText().toString());
        }
        if(lifestyle.isChecked()){
            selected_categories.add(lifestyle.getText().toString());
        }
        if(entertainment.isChecked()){
            selected_categories.add(entertainment.getText().toString());
        }
        if(travel.isChecked()){
            selected_categories.add(travel.getText().toString());
        }
    }
}