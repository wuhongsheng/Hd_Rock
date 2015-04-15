package com.example.hd.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by whs on 2014/11/2.
 */
public  class RockSqliteHelper extends SQLiteOpenHelper {
    public RockSqliteHelper(Context context) {
        super(context,"/sdcard/test.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          // "ROCK.db" db.execSQL("create table Rock (id integer primary key autoincrement,name varchar(20),latitude double,longitude  double)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
