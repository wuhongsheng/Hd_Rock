package com.hd.rock.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hd.app.RockSqliteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whs on 2014/12/5.
 */
public class rockdao {
    private RockSqliteHelper helper;

    public rockdao(Context context) {
        helper = new RockSqliteHelper(context);
    }

    public void Add(String CHAHBA, String CHAHBB, String PKHFF) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("CHAHBA", CHAHBA);
        values.put("CHAHBB", CHAHBB);
        values.put("PKHFF", PKHFF);
        db.insert("C_ROCK_SAMPLE", null, values);

        // db.execSQL("insert into C_ROCK_SAMPLE (CHAHBA,CHAHBB,PKHFF) values (?,?,?)", new Object[]{CHAHBA ,CHAHBB,PKHFF} );
        db.close();
        //return id;

        //db.execSQL("insert into Rock (id,name) values (?,?)", new Object[]{id ,name} );
    }

    public List<Rock> Serch(String mgmax, String mgmin, String mgev, String mgqd) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Rock> rocks = new ArrayList<Rock>();
        Cursor cursor = db.query("C_ROCK_MAGNETISM", null, "DWHACG=?", new String[]{mgev}, null, null, null);
        while ((cursor.moveToNext())) {
            String CHAHBA = cursor.getString(cursor.getColumnIndex("CHAHBA"));
            String CHAHBB = cursor.getString(cursor.getColumnIndex("CHAHBB"));
            String PKHFF = cursor.getString(cursor.getColumnIndex("PKHFF"));
            Rock rock = new Rock(CHAHBB, CHAHBA, PKHFF);
            rocks.add(rock);
        }
        cursor.close();
        db.close();

        return rocks;
    }

    public List<Rock> FindAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Rock> rocks = new ArrayList<Rock>();

        Cursor cursor = db.query("C_ROCK_SAMPLE", null, null, null, null, null, null);
        while ((cursor.moveToNext())) {
            String CHAHBA = cursor.getString(cursor.getColumnIndex("CHAHBA"));
            String CHAHBB = cursor.getString(cursor.getColumnIndex("CHAHBB"));
            String PKHFF = cursor.getString(cursor.getColumnIndex("PKHFF"));
            Rock rock = new Rock(CHAHBB, CHAHBA, PKHFF);
            rocks.add(rock);
        }
        cursor.close();
        db.close();

        return rocks;
    }


 /*   public   FindAll()
    {
        SQLiteDatabase db= helper.getReadableDatabase();
        db.execSQL("insert into Rock (id,name) values (1,\"name\");");
        db.close();

    }*/
}
