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
    TestsLogAdapter testsLogAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_record);
        recyclerView = findViewById(R.id.recyclerViewTestsLog);
        btnClear = findViewById(R.id.btnClear);

        dbHelper = new DBHelper(this, "quizDB", 2);
        db = dbHelper.getWritableDatabase();
        testNo = new ArrayList<>();
        testDate = new ArrayList<>();
        testTime = new ArrayList<>();
        duration = new ArrayList<>();
        correctCount = new ArrayList<>();

        storeTestsLogDataInArrays();
        testsLogAdapter = new TestsLogAdapter(this, testNo, testDate, testTime, duration, correctCount);
        recyclerView.setAdapter(testsLogAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void storeTestsLogDataInArrays() {
        Cursor cursor = dbHelper.readTestsLogData();
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
        recreate();
    }
}