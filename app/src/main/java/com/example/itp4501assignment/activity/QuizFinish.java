package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.itp4501assignment.R;

public class QuizFinish extends AppCompatActivity {

    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_finish);
        tvResult = findViewById(R.id.tvResult);
        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);
        double playTime = getIntent().getDoubleExtra("playTime", 0);
        double averageTime = playTime / 5;
        tvResult.setText("Correct: " + correct + "\nWrong: " + wrong + "\nTotal Play Time: " + playTime + " second" + "\nAverage Play Time: " + averageTime + " second");
    }


}