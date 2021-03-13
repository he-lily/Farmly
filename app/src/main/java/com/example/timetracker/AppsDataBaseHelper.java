
package com.example.timetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.database.Cursor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
The primary function of this class is to serve as a helper to parse information from the Apps Database
It goes through the apps and finds an app to recommend while also retrieving all this importation information
*/

public class AppsDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME         = "GoogleStoreApps1.db";
    private static final int DATABASE_VERSION         = 5;
    private static String DB_PATH = "/data/user/0/com.example.timetracker/databases/";
    SQLiteDatabase AppDataBase1;

    List<String> recommended_apps = new ArrayList<>();
    List<String> has_added = new ArrayList<>();
    List<String> inner_list = new ArrayList<>();
    Map<String,List<String>> to_be_rec = new HashMap<String,List<String>>();

    public AppsDataBaseHelper(@Nullable Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){


    }
    private boolean checkDatabase(){
        try{
            final String mPath = DB_PATH + DATABASE_NAME;
            final File file = new File(mPath);
            if(file.exists())
                return true;
            else
                return false;

        }catch(SQLiteException e){
            e.printStackTrace();
            return false;
        }
    }
    private void copyDatabase() throws IOException{
        try{
            InputStream mInputStream = context.getAssets().open(DATABASE_NAME);
            String outFileName = DB_PATH + DATABASE_NAME;
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while((length = mInputStream.read(buffer)) > 0){
                mOutputStream.write(buffer,0,length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createDatabase() throws IOException{
        boolean mDatabaseExist = checkDatabase();
        if(!mDatabaseExist){
            this.getReadableDatabase();
            this.close();
            try{
                copyDatabase();
            }catch(IOException mIOException){
                mIOException.printStackTrace();
                throw new Error("Error copying database");
            }finally {
                this.close();
            }
        }
    }
    @Override
    public synchronized void close(){
        if(AppDataBase1 != null)
            AppDataBase1.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String,List<String>> loadHandler(){
        try{
            createDatabase();
        }catch (IOException e){
            e.printStackTrace();
        }
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String cat_list = HoldUserInfo.getInstance().getUser_preferred_categories().toString().substring(1, HoldUserInfo.getInstance().getUser_preferred_categories().toString().length() - 1);
        Cursor c = db.rawQuery("select * from App where CATEGORY in (" + cat_list + ")" ,null);
        int prev_score = 0;
        int critera = 30;
        while(c.moveToNext()){
            String app_name = c.getString(0);
            String app_id = c.getString(1);
            String app_category = c.getString(2);
            String app_url = c.getString(3);
            String app_logo = c.getString(4);
            Float app_rating_score = c.getFloat(5);
            int app_review_count = c.getInt(6);
            int app_downloads = c.getInt(7);
            float app_price = c.getFloat(8);
            int base_line = c.getInt(9);



            if(recommended_apps == null || recommended_apps.size() < 4 && (HoldUserInfo.getInstance().getUser_has_been_recommended() == null
                    || !HoldUserInfo.getInstance().getUser_has_been_recommended().contains(app_name))){
                if(has_added == null || (has_added.size() <= HoldUserInfo.getInstance().getUser_preferred_categories().size())){
                    if(has_added == null || !has_added.contains(app_category)) {
                        if(critera > 0 && critera < 30 && prev_score < 18 && base_line < 18){
                            continue;
                        }
                        prev_score = base_line;
                        recommended_apps.add(app_name);
                        has_added.add(app_category);
                        inner_list.add(app_url);
                        inner_list.add(app_logo);
                        to_be_rec.put(app_name,inner_list);
                        inner_list=new ArrayList<String>();
                        critera = critera - base_line;
                    }
                    else if(HoldUserInfo.getInstance().getUser_preferred_categories().size() == has_added.size()){
                        has_added.clear();
                    }
                }
            }
            if(recommended_apps.size() == 3){
                break;
            }


        }
        HoldUserInfo.getInstance().setUser_has_been_recommended(recommended_apps);
        System.out.println("TO BE REC" + to_be_rec);
        c.close();
        db.close();
        return to_be_rec;
    }


}
