package com.example.timetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//this class displays the UI for user plan
public class FragmentPlan extends Fragment {
    HashMap<String,Double> catUsage;   //usage (in seconds) for each category chosen by the user during onboarding
    List<String> catPref;  //a list of the user's preferred category(s) of apps they want to spend more time on
    List<Double> prefTimes; //the time (in seconds) a user has spent on each "preferred" app as chosen during onboarding

    Map<String,List<String>> recApps;
    HashMap<String,Double> catUsage;
    RecyclerView rv;
    List<String> catPref;
    List<Double> prefTimes = new ArrayList<>();
    ImageView recImage1;
    ImageView recImage2;
    ImageView recImage3;
    TextView recName1;
    TextView recName2;
    TextView recName3;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //load data from database into these variables
        catUsage = (HashMap<String,Double>)HoldUserInfo.getInstance().getUser_app_usage().get(2); //Map for usage of all categories
        catPref = HoldUserInfo.getInstance().getUser_preferred_categories(); //String List for preferred categories

        for (int i = 0; i < catPref.size(); ++i){
            try{
                prefTimes.add(i, catUsage.get(catPref.get(i).replace("\"", "")));
            }
            catch (Exception e) {
                System.out.println("COULD NOT ADD: " + catPref.get(i));
            }
        }

        //create plan UI fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);

        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        rv = view.findViewById(R.id.recyclerViewPLAN);
        FragmentPlanAdapter ad = new FragmentPlanAdapter(getActivity(), catPref, prefTimes);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setAdapter(ad);

        recApps = HoldUserInfo.getInstance().getUser_to_be_rec();
        Iterator<String> itr = recApps.keySet().iterator();
        String firstKey = itr.next();
        List<String> ra1 = recApps.get(firstKey);
        String secondKey = itr.next();
        List<String> ra2 = recApps.get(secondKey);
        String thirdKey = itr.next();
        List<String> ra3 = recApps.get(thirdKey);

        recImage1 = view.findViewById(R.id.recImage1);
        Picasso.with(view.getContext()).load(ra1.get(1)).into(recImage1);
        recName1 = view.findViewById(R.id.recName1);
        recName1.setText(firstKey);
        recImage2 = view.findViewById(R.id.recImage2);
        Picasso.with(view.getContext()).load(ra2.get(1)).into(recImage2);
        recName2 = view.findViewById(R.id.recName2);
        recName2.setText(secondKey);
        recImage3 = view.findViewById(R.id.recImage3);
        Picasso.with(view.getContext()).load(ra3.get(1)).into(recImage3);
        recName3 = view.findViewById(R.id.recName3);
        recName3.setText(thirdKey);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
