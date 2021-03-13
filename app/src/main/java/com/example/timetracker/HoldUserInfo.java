package com.example.timetracker;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//this class provides get/set methods for data in DB
public class HoldUserInfo {
    private String user_name;
    private String user_email;
    private String user_job;
    private int user_age;
    private List<Object> app_usage = new ArrayList<>();
    private List<String> disliked_apps;
    private List<String> preferred_categories;
    private List<String> has_been_recommended;
    private Map<String,List<String>> to_be_rec;
    private Map<String,String> all_apps;

    public String getUser_name() {return user_name;}
    public void setUser_name(String user_name) {this.user_name = user_name;}

    public String getUser_email() {return user_email;}
    public void setUser_email(String user_email) {this.user_email = user_email;}

    public String getUser_job() {return user_job;}
    public void setUser_job(String user_job) {this.user_job = user_job;}

    public int getUser_age() {return user_age;}
    public void setUser_age(int user_age) {this.user_age = user_age;}

    public List<Object> getUser_app_usage() {return app_usage;}
    public void setUser_app_usage(List<Object> app_usage) {this.app_usage= app_usage;}

    public List<String> getUser_disliked_apps() {return disliked_apps;}
    public void setUser_disliked_apps(List<String> disliked_apps) {this.disliked_apps = disliked_apps;}

    public List<String> getUser_preferred_categories() {return preferred_categories;}
    public void setUser_preferred_categories(List<String> preferred_categories) {this.preferred_categories = preferred_categories;}

    public List<String> getUser_has_been_recommended() {return has_been_recommended;}
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setUser_has_been_recommended(List<String> has_been_recommended) {
        if(this.has_been_recommended == null){
            this.has_been_recommended = has_been_recommended;
        }
        else{
            this.has_been_recommended.addAll(has_been_recommended);
        }
    }
    public void setUser_to_be_rec(Map<String,List<String>> to_be_rec){this.to_be_rec = to_be_rec;}
    public Map<String,List<String>>  getUser_to_be_rec() {return to_be_rec;}

    public void setUser_all_apps(Map<String,String> all_apps){this.all_apps = all_apps;}
    public Map<String,String>  getUser_all_apps() {return all_apps;}

    private static final HoldUserInfo holder = new HoldUserInfo();
    public static HoldUserInfo getInstance() {return holder;}
}

