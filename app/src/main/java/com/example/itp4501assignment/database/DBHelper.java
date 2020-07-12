package com.example.itp4501assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * implement the database create, insert, update, delete
 */
public class DBHelper extends SQLiteOpenHelper {
    // variable dictionary
    String sql;
    private static int VERSION = 1;
    long testNo;
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
        sql = "CREATE TABLE TestsLog(testNo INTEGER PRIMARY KEY AUTOINCREMENT, testDate text, testTime text, duration real, correctCount int)";
        db.execSQL(sql);

        sql = "CREATE TABLE QuestionsLog(questionNo INTEGER PRIMARY KEY AUTOINCREMENT, question text, yourAnswer int, isCorrect INTEGER, testNo int," +
                "FOREIGN KEY (testNo) REFERENCES TestsLog(testNo));";
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

    // add TestLogs Record
    public void addTestsLogRecord(double elapsedTime, int correct) {
        // get database
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValues and put the data to ContentValues
        ContentValues testsLog = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        testsLog.put("testDate", date);
        sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        testsLog.put("testTime", time + "");
        testsLog.put("duration", elapsedTime + "");
        testsLog.put("correctCount", correct + "");

        // insert ContentValues to table
        testNo = db.insert("TestsLog", "", testsLog);
    }

    public void addQuestionsRecord(String question, int yourAnswer, int isCorrect) {
        // get database
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValues and put the data to ContentValues
        ContentValues questionsLog = new ContentValues();
        questionsLog.put("question", question + "");
        questionsLog.put("yourAnswer", (yourAnswer + ""));
        questionsLog.put("isCorrect", (isCorrect + ""));
        questionsLog.put("testNo", testNo + "");

        // insert ContentValues to table
        db.insert("QuestionsLog", null, questionsLog);

    }

    public Cursor readQuestionsLogData(int testNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] column = {"questionNo", "question", "yourAnswer", "isCorrect"};
        String[] args = {testNo + ""};
        Cursor cursor= db.query("QuestionsLog", column, "testNo = ?", args, null, null, null, null);
        return cursor;
    }
}
