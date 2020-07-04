package com.example.itp4501assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class QuizFinish extends AppCompatActivity {

    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_finish);
        tvResult = findViewById(R.id.tvResult);
        int correct = getIntent().getIntExtra("corrent", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);
        tvResult.setText("Correct: " + correct + "\nWrong: " + wrong);
    }


}