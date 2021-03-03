
package com.example.timetracker;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;
        import android.database.Cursor;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;

        import androidx.annotation.Nullable;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class AppsDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME         = "GoogleStoreApps.db";
    private static final int DATABASE_VERSION         = 3;
    private static String DB_PATH = "/data/user/0/com.example.timetracker/databases/";
    SQLiteDatabase AppDataBase;
    List<String> recommended_apps = new ArrayList<>();

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
        if(AppDataBase != null)
            AppDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    public List<String> loadHandler(){
        try{
            createDatabase();
        }catch (IOException e){
            e.printStackTrace();
        }
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from App",null);

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
            System.out.println("APP CATEGORY: " + app_category);
            if(recommended_apps == null || recommended_apps.size() < 4 && HoldUserInfo.getInstance().getUser_has_been_recommended() == null || !HoldUserInfo.getInstance().getUser_has_been_recommended().contains(app_name)
                    && HoldUserInfo.getInstance().getUser_preferred_categories().contains(app_category)){
                recommended_apps.add(app_name);
            }
            if(recommended_apps.size() == 3){
                break;
            }

        }
        c.close();
        db.close();
        return recommended_apps;
    }


}