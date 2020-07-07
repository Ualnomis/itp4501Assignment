package com.example.itp4501assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Cursor readTestsLogData() {
        String sql = "SELECT * FROM TestsLog";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql, null);

        }
        System.out.println("DBH:     " + cursor);
        return cursor;
    }


    public void addTestsLogRecord(double elapsedTime, int correct) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues testsLog = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        testsLog.put("testDate", date);
        sdf = new SimpleDateFormat("HH:mm:ss");
        date = sdf.format(new Date());
        testsLog.put("testTime", date + "");
        testsLog.put("duration", elapsedTime + "");
        testsLog.put("correctCount", correct + "");
        db.insert("TestsLog", null, testsLog);
    }

    public void addQuestionsRecord(String question, int yourAnswer, int isCorrect) {
        ContentValues questionsLog = new ContentValues();
        SQLiteDatabase db = this.getReadableDatabase();

        questionsLog.put("question", question + "");
        questionsLog.put("yourAnswer", (yourAnswer + ""));
        questionsLog.put("isCorrect", (isCorrect + ""));
        db.insert("QuestionsLog", null, questionsLog);
    }
}
