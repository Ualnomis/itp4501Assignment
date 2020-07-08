package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.itp4501assignment.R;

public class QuizFinish extends AppCompatActivity {

    private TextView tvCorrect, tvWrong, tvTotalPlayTime, tvAvgPlayTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_finish);
        tvCorrect = findViewById(R.id.tvCorrect);
        tvWrong = findViewById(R.id.tvWrong);
        tvTotalPlayTime = findViewById(R.id.tvTotalPlayTime);
        tvAvgPlayTime = findViewById(R.id.tvAvgPlayTime);


        int correct = getIntent().getIntExtra("correct", 0);
//        String correct = getIntent().getStringExtra("correct");
        int wrong = getIntent().getIntExtra("wrong", 0);
        double playTime = getIntent().getDoubleExtra("playTime", 0);
        double averageTime = playTime / 5;
        tvCorrect.setText("Correct: " + correct);
        tvWrong.setText("Wrong: " + wrong);
        tvTotalPlayTime.setText("Total Play Time: " + playTime + " second");
        tvAvgPlayTime.setText("Average Play Time: " + averageTime + " second");
    }


}