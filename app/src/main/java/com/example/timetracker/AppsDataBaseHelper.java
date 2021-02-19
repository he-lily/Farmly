
package com.example.timetracker;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;

        import androidx.annotation.Nullable;

        import java.util.List;

public class AppsDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME         = "Apps.db";
    private static final int DATABASE_VERSION         = 1;

    private static final String TABLE_NAME             = "Apps";
    private static String COLUMN_ID                    = "_id";
    private static String COLUMN_CATEGORY              = "app_category";
    private static String COLUMN_IOS_COMPATIBILITY     = "app_ios_compatibility";
    private static String COLUMN_ANDROID_COMPATIBILITY = "app_android_compatibility";
    private static String COLUMN_RECOMMENDATION_SCORE  = "app_recommendation_score";
    private static String COLUMN_APP_NAME              = "app_name";

    public AppsDataBaseHelper(@Nullable Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String query =
                "CREATE TABLE " + TABLE_NAME + " ("  +
                        COLUMN_ID                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CATEGORY              + " TEXT, " +
                        COLUMN_IOS_COMPATIBILITY     + " BOOL, "   +
                        COLUMN_ANDROID_COMPATIBILITY + " BOOL, " +
                        COLUMN_APP_NAME              + "TEXT, "   +
                        COLUMN_RECOMMENDATION_SCORE  + " FLOAT);";

        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addAppRecRank(String app_category, boolean app_ios_compat, boolean app_android_compat, float app_recommendation_score, String app_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();

        cv.put(COLUMN_CATEGORY,app_category);
        cv.put(COLUMN_IOS_COMPATIBILITY,app_ios_compat);
        cv.put(COLUMN_ANDROID_COMPATIBILITY,app_android_compat);
        cv.put(COLUMN_RECOMMENDATION_SCORE,app_recommendation_score);
        cv.put(COLUMN_APP_NAME,app_name);


        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
    }

}