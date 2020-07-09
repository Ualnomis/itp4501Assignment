package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.itp4501assignment.MainActivity;
import com.example.itp4501assignment.R;
import com.example.itp4501assignment.animation.MyButtonInterpolator;
import com.example.itp4501assignment.asyncTask.MyAsyncTask;
import com.example.itp4501assignment.asyncTask.OnDownloadFinishListener;

public class QuizFinish extends AppCompatActivity implements OnDownloadFinishListener {

    MyAsyncTask task = null;

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

    public void onClickReplay(View view) {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);

        task=new MyAsyncTask();
        new MyAsyncTask(this).execute();
    }

    public void onClickHome(View view) {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateDownloadResult(String result) {
        Intent intent;
        intent = new Intent(this, QuizActivity.class);
        intent.putExtra("json", result);
        startActivity(intent);
    }
}