package com.example.timetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentGoals extends Fragment {
    HashMap<String,Double> appUsage;
    RecyclerView rv;
    List<String> appDis;
    List<Double> appTimes = new ArrayList<>();
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        appUsage = (HashMap<String,Double>)HoldUserInfo.getInstance().getUser_app_usage().get(4); //Map for usage of all apps
        appDis = HoldUserInfo.getInstance().getUser_disliked_apps(); //String List for disliked apps

        System.out.println("HAHAHAHA " +appUsage);
        System.out.println("FIRST KEY " + appUsage.keySet().iterator().next());
        for (int i = 0; i < appDis.size(); ++i){
            try{
                System.out.println(appDis.get(i));
                appTimes.add(i, appUsage.get(appDis.get(i)));
            }
            catch (Exception e) {
                System.out.println("COULD NOT ADD: " + appDis.get(i));
            }
        }

        rv = view.findViewById(R.id.recyclerViewGOAL);
        FragmentPlanAdapter ad = new FragmentPlanAdapter(getActivity(), appDis, appTimes);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setAdapter(ad);

        return view;
    }

}
