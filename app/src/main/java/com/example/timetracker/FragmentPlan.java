package com.example.timetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//this class displays the UI for user plan
public class FragmentPlan extends Fragment {
    HashMap<String,Double> catUsage;   //usage (in seconds) for each category chosen by the user during onboarding
    List<String> catPref;  //a list of the user's preferred category(s) of apps they want to spend more time on
    List<Double> prefTimes; //the time (in seconds) a user has spent on each "preferred" app as chosen during onboarding
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //load data from database into these variables
        catUsage = (HashMap<String,Double>)HoldUserInfo.getInstance().getUser_app_usage().get(2); //Map for usage of all categories
        catPref = HoldUserInfo.getInstance().getUser_preferred_categories(); //String List for preferred categories

        System.out.println(catUsage);
        System.out.println(catPref);

        //Get usage for preferred categories
        for (int i = 0; i < catPref.size(); ++i){
            try{
                prefTimes.add(i, catUsage.get(catPref.get(i)));
            }
            catch (Exception e) {
                System.out.println("COULD NOT ADD: " + catPref.get(i));
            }
        }
        //create plan UI fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

}
