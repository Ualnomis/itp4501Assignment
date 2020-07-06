package com.example.itp4501assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizRecord extends AppCompatActivity {

    String dataStr = "";
    TextView tvRecord;
    Button btnClear;
    DBHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_record);
        tvRecord = findViewById(R.id.tvRecord);
        btnClear = findViewById(R.id.btnClear);

        dbHelper = new DBHelper(this, "quizDB", 2);
        db = dbHelper.getWritableDatabase();
        showRecord();
    }

    private void showRecord() {
        System.out.println("dataStr" + dataStr);
        String sql = "SELECT * FROM TestsLog";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
                int testNo = cursor.getInt(cursor.getColumnIndex("testNo"));
                String testDate = cursor.getString(cursor.getColumnIndex("testDate"));
                String testTime = cursor.getString(cursor.getColumnIndex("testTime"));
                double duration = cursor.getDouble(cursor.getColumnIndex("duration"));
                int correctCount = cursor.getInt(cursor.getColumnIndex("correctCount"));
                dataStr += String.format("%d %-12s %-9s %.2f %d\n", testNo, testDate, testTime, duration, correctCount);
        }
        tvRecord.setText(dataStr);
    }

    public void onClickClearRecord(View view) {
        deleteDatabase("quizDB");
        showRecord();
    }
}