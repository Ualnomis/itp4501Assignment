package com.example.itp4501assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    // variable dictionary
    String sql;
    private static int VERSION = 1;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context,String name,int version) {
        this(context,name,null, version);
    }

    public DBHelper(Context context,String name) {
        this(context, name, VERSION);
    }


    // use to create the table when the table not exist in database
    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "CREATE TABLE QuestionsLog(questionNo INTEGER PRIMARY KEY AUTOINCREMENT, question text, yourAnswer int, isCorrect INTEGER)";
        db.execSQL(sql);
        sql = "CREATE TABLE TestsLog(testNo INTEGER PRIMARY KEY AUTOINCREMENT, testDate text, testTime text, duration real, correctCount int)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Update the database");
    }
}
