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


/*
 * used to display the result
 */
public class QuizFinish extends AppCompatActivity implements OnDownloadFinishListener {

    // variable dictionary
    MyAsyncTask task = null; // create AsyncTask
    private TextView tvCorrect, tvWrong, tvTotalPlayTime, tvAvgPlayTime; // use to store the uml TextView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_finish);

        // find view by id
        tvCorrect = findViewById(R.id.tvCorrect);
        tvWrong = findViewById(R.id.tvWrong);
        tvTotalPlayTime = findViewById(R.id.tvTotalPlayTime);
        tvAvgPlayTime = findViewById(R.id.tvAvgPlayTime);


        // get the required data from intent
        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);
        double playTime = getIntent().getDoubleExtra("playTime", 0);

        // calculate average time
        double averageTime = playTime / 5;

        // set all require data to TextView
        tvCorrect.setText("Correct: " + correct);
        tvWrong.setText("Wrong: " + wrong);
        tvTotalPlayTime.setText("Total Play Time: " + playTime + " second");
        tvAvgPlayTime.setText("Average Play Time: " + averageTime + " second");
    }

    // replay when replay button clicked
    public void onClickReplay(View view) {
        // click animation
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);

        // call MyAsyncTask(); to put the json to intent and start the intent
        task=new MyAsyncTask();
        // execute updateDownloadResult(String result);
        new MyAsyncTask(this).execute();
    }

    // go to home when replay button clicked
    public void onClickHome(View view) {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);

        // go to the home page
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // put the json to intent and start the intent
    @Override
    public void updateDownloadResult(String result) {
        // create intent
        Intent intent;
        // set the intent to QuizAcivity
        intent = new Intent(this, QuizActivity.class);
        // put json to intent
        intent.putExtra("json", result);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // start activity
        startActivity(intent);
    }
}