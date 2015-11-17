package com.example.panchangtao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNew(View view){
        Log.d("PCT", "onClickNew\n");
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this, "sqlite");
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
    }
    public void onClickUpdate(View view){
        Log.d("PCT", "onClickUpdate\n");
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this, "sqlite", 2);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", "zhangsanfeng");
        sqLiteDatabase.update("user", values, "id=?", new String[]{"1"});
    }
    public void onClickAdd(View view){
        Log.d("PCT", "onClickAdd\n");
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 1);
        contentValues.put("name", "zhangsan");
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this, "sqlite", 2);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.insert("user", null, contentValues);//表明，null，值
    }
    public void onClickQuery(View view) {
        Log.d("PCT", "onClickQuery\n");
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this, "sqlite");
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("user", new String[]{"id", "name"}, "id=?", new String[]{"1"}, );
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Log.d("PCT", name + "");
        }
    }
}
