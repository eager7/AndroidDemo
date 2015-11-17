package com.example.panchangtao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * Created by panchangtao on 2015/11/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private final  static int VERSION = 1;
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//调用父类的构造函数
    }

    public DatabaseHelper(Context mContext, String name){
        this(mContext, name, VERSION);
    }

    public DatabaseHelper(Context mContext, String name, int iVersion){
        this(mContext, name, null, iVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("PCT", "onCreate\n");
        db.execSQL("create table user(id int, name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("PCT", "onUpgrade\n");
    }
}
