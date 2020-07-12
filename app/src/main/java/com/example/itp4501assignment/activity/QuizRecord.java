package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itp4501assignment.animation.MyButtonInterpolator;
import com.example.itp4501assignment.database.DBHelper;
import com.example.itp4501assignment.R;
import com.example.itp4501assignment.recyclerViewAdapter.TestsLogAdapter;

import java.util.ArrayList;

/*
 * used to display date and time you play a test, corresponding duration to complete a test
 * and how many answers questions are correct
 */
public class QuizRecord extends AppCompatActivity {

    // variable dictionary
    Button btnClear; // set Button
    DBHelper dbHelper; // (SQLiteOpenHelper) use to insert update delete database
    SQLiteDatabase db; // // store the database get from DBhelper
    private ArrayList<String> testNo, testDate, testTime, duration, correctCount; // create ArrayList to store the database record
    TestsLogAdapter testsLogAdapter; // recyclerView Adapter use to bind the data from TestsLog table from database
    RecyclerView recyclerView; // recycler view reference
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_record);
        // find view by id
        recyclerView = findViewById(R.id.recyclerViewTestsLog);
        btnClear = findViewById(R.id.btnClear);

        // set up database
        dbHelper = new DBHelper(this, "quizDB", 2);
        // get database from dbhelper
        db = dbHelper.getWritableDatabase();

        // create all column to arraylist
        testNo = new ArrayList<>();
        testDate = new ArrayList<>();
        testTime = new ArrayList<>();
        duration = new ArrayList<>();
        correctCount = new ArrayList<>();

        // call function to store testsLog data in arraylist
        storeTestsLogDataInArrays();

        // set up the TestLogsAdapter
        testsLogAdapter = new TestsLogAdapter(this, testNo, testDate, testTime, duration, correctCount);
        // set the recycler adapter to testsLogAdapter
        recyclerView.setAdapter(testsLogAdapter);
        // display the data to recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    // store testsLog data in arraylist
    private void storeTestsLogDataInArrays() {
        // read all TestsLog record
        Cursor cursor = dbHelper.readTestsLogData();
        System.out.println(cursor);

        if (cursor.getCount() == 0) { // if there are no record
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        } else { // if have record, put all data to arraylist
            while (cursor.moveToNext()) { // get each record in table
                // add the column to arrayList
                testNo.add(cursor.getString(cursor.getColumnIndex("testNo")));
                testDate.add(cursor.getString(cursor.getColumnIndex("testDate")));
                testTime.add(cursor.getString(cursor.getColumnIndex("testTime")));
                duration.add(cursor.getString(cursor.getColumnIndex("duration")));
                correctCount.add(cursor.getString(cursor.getColumnIndex("correctCount")));
            }
        }
    }

    // clear all the record after click the button
    public void onClickClearRecord(View view) {
        // click animation
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);

        // clear all the data from arraylist
        testNo.clear();
        testDate.clear();
        testTime.clear();
        duration.clear();
        correctCount.clear();

        // delete database
        deleteDatabase("quizDB");

        // reset the recycler view to empty
        testsLogAdapter = new TestsLogAdapter(this, testNo, testDate, testTime, duration, correctCount);
        recyclerView.setAdapter(testsLogAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // show the chart after click the show chart button
    public void onClickShowChart(View view) {
        // click animation
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);

        // go to the ChartActivity to show the chart
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }
}