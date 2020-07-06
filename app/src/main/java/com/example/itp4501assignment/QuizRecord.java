package com.example.itp4501assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizRecord extends AppCompatActivity {

    String dataStr = "";
    TextView tvRecord;
    Button btnClear;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ArrayList<String> testNo, testDate, testTime, duration, correctCount;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_record);
        recyclerView = findViewById(R.id.recyclerViewTestsLog);
        tvRecord = findViewById(R.id.tvRecord);
        btnClear = findViewById(R.id.btnClear);

        dbHelper = new DBHelper(this, "quizDB", 2);
        db = dbHelper.getWritableDatabase();
        testNo = new ArrayList<>();
        testDate = new ArrayList<>();
        testTime = new ArrayList<>();
        duration = new ArrayList<>();
        correctCount = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(this, testNo, testDate, testTime, duration, correctCount);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void storeDataInArrays() {
        Cursor cursor = dbHelper.readAllData();
        System.out.println(cursor);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                testNo.add(cursor.getString(cursor.getColumnIndex("testNo")));
                testDate.add(cursor.getString(cursor.getColumnIndex("testDate")));
                testTime.add(cursor.getString(cursor.getColumnIndex("testTime")));
                duration.add(cursor.getString(cursor.getColumnIndex("duration")));
                correctCount.add(cursor.getString(cursor.getColumnIndex("correctCount")));
            }
        }


    }

    public void onClickClearRecord(View view) {
        deleteDatabase("quizDB");
        showRecord();
    }
}