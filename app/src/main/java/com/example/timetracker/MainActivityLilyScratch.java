package com.example.timetracker;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityLilyScratch extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lily_scratch);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //arg item = the menu item that's been selected
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        //check which menu button was selected
                        //that fragment will be created but not shown yet
                        case R.id.nav_plan:
                            selectedFragment=new FragmentPlan();
                            break;

                        case R.id.nav_search:
                            selectedFragment=new FragmentSearch();
                            break;

                        case R.id.nav_goals:
                            selectedFragment=new FragmentGoals();
                            break;
                    }

                    //args: container where we want to display the fragment in, fragment to show
                    //this line dispalys the selected fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    //true means we want to highlight the selected item
                    //return false would not highlight the selected item, but still dispaly the fragment
                    return true;
                }
            };
}
