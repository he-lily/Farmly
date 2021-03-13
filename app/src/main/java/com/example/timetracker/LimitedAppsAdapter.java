package com.example.timetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class LimitedAppsAdapter extends RecyclerView.Adapter<LimitedAppsAdapter.ViewHolder> {
    List<String> mAppNames;
    List<Double> mAppTimes;
    Context mContext;

    public LimitedAppsAdapter(Context context, List<String> appNames, List<Double> appTimes){
        mContext = context;
        mAppNames = appNames;
        mAppTimes = appTimes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_limited_apps, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.limitedApp.setText(mAppNames.get(position));
        holder.limitedTime.setText("0 mins");
        if(HoldUserInfo.getInstance().getUser_all_apps().containsKey(mAppNames.get(position))) {
            Picasso.with(mContext).load(HoldUserInfo.getInstance().getUser_all_apps().get(mAppNames.get(position))).into(holder.myImage);
        }else{
            Picasso.with(mContext).load("https://play-lh.googleusercontent.com/O-7F6uiQOUbCPpfa80BldoKEiNDUekj5NBXYiJvx8fpZxT_Uuw0iAYHIKNa7yUXHbbs").into(holder.myImage);
        }
        holder.limitedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.limitedTime.setText("" + progress + " mins");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView limitedApp;
        TextView limitedTime;
        SeekBar limitedBar;
        ImageView myImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            limitedApp = itemView.findViewById(R.id.limitedApp);
            myImage = itemView.findViewById(R.id.app_icon1);
            limitedTime = itemView.findViewById(R.id.limitedTime);
            limitedBar = itemView.findViewById(R.id.limitedBar);
        }
    }
}
