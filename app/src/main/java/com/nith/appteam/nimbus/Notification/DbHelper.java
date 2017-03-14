package com.nith.appteam.nimbus.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nith.appteam.nimbus.Model.HomePostsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jatin on 7/3/17.
 */

public class DbHelper extends SQLiteOpenHelper{

    SQLiteDatabase mdb;

    private static final int DATABASE_VERSION      = 2;

    private static final String DB_NAME = "onesignalnotiication";
    // HOME POSTS TABLE
    private static final String TABLE_HOMEPOST= "notification";
    private static final String NOTIFICATION_ID="id";
    private static final String NOTIFICATION_small_icon="smallicon";
    private static final String NOTIFICATION_TITLE="title";
    private static final String NOTIFICATION_TIMESTAMP="timestamp";
    private static final String NOTIFICATION_LARGE_ICON="largeicon";
    private static final String NOTIFICATION_BODY="body";
    private static final String NOTIFICATION_BIG_PICTURE="bigpicture";
    private static final String NOTIFICATION_LAUNCH_URL="launchurl";
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        mdb=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  " +TABLE_HOMEPOST+ "( "
                +NOTIFICATION_ID+ " text  , "
                +NOTIFICATION_small_icon+ " text , "
                +NOTIFICATION_TITLE+ " text , "
                +NOTIFICATION_BODY+" text , "
                +NOTIFICATION_LARGE_ICON+" text , "
                +NOTIFICATION_BIG_PICTURE+" text , "
                +NOTIFICATION_LAUNCH_URL+" text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {

            //  sqLiteDatabase.execSQL(DATABASE_ALTER_TEAM_1);
            db.execSQL("drop table if exists " + TABLE_HOMEPOST);
            onCreate(db);
        }
    }

    public boolean truncate(String tablename) {
        SQLiteDatabase db = getWritableDatabase();
        if (tablename.equals("homeposts")) {
            db.execSQL("delete from " + TABLE_HOMEPOST);
            return true;
        }
        return false;
    }

    public boolean insert_2_homeposts(String id, String smallicon, String title, String body, String bigpicture, String largeicon,String launchurl) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTIFICATION_ID, id);
        contentValues.put(NOTIFICATION_small_icon, smallicon);
        contentValues.put(NOTIFICATION_TITLE, title);
        contentValues.put(NOTIFICATION_BODY, body);
        contentValues.put(NOTIFICATION_BIG_PICTURE, bigpicture);
        contentValues.put(NOTIFICATION_LARGE_ICON, largeicon);
        contentValues.put(NOTIFICATION_LAUNCH_URL, launchurl);
        db.insert(TABLE_HOMEPOST, null, contentValues);
        return true;
    }

    public List gethomedata() {

        Log.d("TAGGGGG", "insideeeeeee gethomedata");
        List<HomePostsModel> homedetails = new ArrayList<>();
        String query = "select * from " + TABLE_HOMEPOST;
        SQLiteDatabase db = getReadableDatabase();
        mdb = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.d("LOG", "cursorr" + cursor.toString());
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        HomePostsModel homeposts = new HomePostsModel();
                        String id = cursor.getString(cursor.getColumnIndex(NOTIFICATION_ID));
                        String title = cursor.getString(cursor.getColumnIndex(NOTIFICATION_TITLE));
                        String small_icon = cursor.getString(cursor.getColumnIndex(NOTIFICATION_small_icon));

                        Log.d("TAG", "valuesss" +id+title+small_icon);
                        homeposts.setNotification_id(id);
                        homeposts.setTitle(title);
                        homeposts.setSmall_icon(small_icon);
                        homedetails.add(homeposts);
                    } while (cursor.moveToNext());

                    for (int i = 0; i < homedetails.size(); i++) {
                        Log.d("LOG", "arrryyyy" + homedetails.get(i).toString());
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                Log.d("TAG", "Error while trying to get posts from database");
            }

        } else {
            Log.d("DB", "cursor emptyyyy");
        }

        return homedetails;
    }

    public boolean checkidrepeated(String id){
        String query="select id from "+TABLE_HOMEPOST+" where id = '"+id+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor!=null)
            return true;
        return false;
    }

    public Cursor homeposteinnerdata(String id) {
        String query = "select * from " + TABLE_HOMEPOST + "  where  id = '" + id+"'" ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }


}
