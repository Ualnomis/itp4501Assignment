package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.itp4501assignment.R;
import com.example.itp4501assignment.database.DBHelper;
import com.example.itp4501assignment.entity.BarChartEntity;
import com.example.itp4501assignment.widget.BarChart;

import java.util.ArrayList;
import java.util.List;

/*
 * Download ten IQ Test questions from a server
 * and then ask user to randomly answer five of them one by one.
 * Use radio buttons which randomly create three incorrect choices
 * and are close to the value of correct one
 */
public class ChartActivity extends AppCompatActivity {
    // variable dictionary
    DBHelper dbHelper; // (SQLiteOpenHelper) use to insert update delete database
    SQLiteDatabase db; // store the database get from DBhelper
    List<BarChartEntity> datas; // store list of datas that need to be bar chart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // connect the database
        dbHelper = new DBHelper(this, "quizDB", 2);
        db = dbHelper.getWritableDatabase();

        // set the bar chart view
        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        // create arraylist object
        datas = new ArrayList<>();

        // call loadTestsLogDataToChart()
        loadTestsLogDataToChart();

        // set the x Axis Unit Name
        String xAxisUnitName = "Test No";
        // set the y Axis Unit Name
        String yAxisUnitName = "Correct";
        // put the data to BarChart object to generate bar chart
        barChart.setData(datas,new int[]{Color.parseColor("#6FC5F4")}, xAxisUnitName, yAxisUnitName);
        barChart.startAnimation();
    }

    // load TestsLog data from database to chart array
    private void loadTestsLogDataToChart() {
        Cursor cursor = dbHelper.readTestsLogData();
        System.out.println(cursor);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                String testNo = cursor.getString(cursor.getColumnIndex("testNo"));
                int correctCount = (cursor.getInt(cursor.getColumnIndex("correctCount")));
                datas.add(new BarChartEntity(testNo, new Float[]{(float) (correctCount)}));
            }
        }
    }

}