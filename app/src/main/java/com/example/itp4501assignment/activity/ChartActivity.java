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

public class ChartActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;
    List<BarChartEntity> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        dbHelper = new DBHelper(this, "quizDB", 2);
        db = dbHelper.getWritableDatabase();

        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        datas = new ArrayList<>();

        loadTestsLogDataToChart();
        String xAxisUnitName = "Test No";
        String yAxisUnitName = "Correct";
        barChart.setData(datas,new int[]{Color.parseColor("#6FC5F4")}, xAxisUnitName, yAxisUnitName);
        barChart.startAnimation();
    }


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