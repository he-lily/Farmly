
package com.example.timetracker;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;

        import androidx.annotation.Nullable;

        import java.util.List;

//this class creates the DB for user ranked apps that we will recommend them
//includes CRUD functionality
public class RecommendationRankDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME         = "RecommendationRank.db";
    private static final int DATABASE_VERSION         = 1;

    private static final String TABLE_NAME            = "RecommendationRank";
    private static String COLUMN_ID                   = "_id";
    private static String COLUMN_PRICE                = "app_price";
    private static String COLUMN_DOWNLOADS            = "app_downloads";
    private static String COLUMN_AVERAGE_REVIEW_SCORE = "app_review_score";
    private static String COLUMN_COMPATIBILITY_SCORE  = "app_compat_score";
    private static String COLUMN_APP_NAME             = "app_name";

    public RecommendationRankDBHelper(@Nullable Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String query =
                        "CREATE TABLE " + TABLE_NAME + " ("  +
                        COLUMN_ID                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PRICE                 + " FLOAT, " +
                        COLUMN_DOWNLOADS             + " INT, "   +
                        COLUMN_AVERAGE_REVIEW_SCORE  + " FLOAT, " +
                        COLUMN_APP_NAME              + "TEXT, "   +
                        COLUMN_COMPATIBILITY_SCORE   + " FLOAT);";

        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addAppRecRank(float app_price, int app_downloads, float app_review_score, float app_compat_score,String app_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();

        cv.put(COLUMN_PRICE,app_price);
        cv.put(COLUMN_DOWNLOADS,app_downloads);
        cv.put(COLUMN_AVERAGE_REVIEW_SCORE,app_review_score);
        cv.put(COLUMN_COMPATIBILITY_SCORE,app_compat_score);
        cv.put(COLUMN_APP_NAME,app_name);


        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
    }

}
