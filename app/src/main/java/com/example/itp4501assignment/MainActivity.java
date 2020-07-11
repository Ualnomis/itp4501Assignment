package com.example.itp4501assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.itp4501assignment.activity.QuizActivity;
import com.example.itp4501assignment.activity.QuizRecord;
import com.example.itp4501assignment.animation.MyButtonInterpolator;
import com.example.itp4501assignment.asyncTask.MyAsyncTask;
import com.example.itp4501assignment.asyncTask.OnDownloadFinishListener;

/*
 * Home page of the app
 * used to go to QuizActivity and QuizRecord
 */
public class MainActivity extends AppCompatActivity implements OnDownloadFinishListener {

    String json;
    MyAsyncTask task=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Intent intent;

        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        v.startAnimation(myAnim);

        switch (v.getId()) {
            case R.id.btnStart:
                System.out.println("Start the quiz");
                // load json
                task=new MyAsyncTask();
                // execute updateDownloadResult(String result);
                new MyAsyncTask(this).execute();

                break;

            case R.id.btnRecord:
                // set intent to QuizRecord
                intent = new Intent(this, QuizRecord.class);
                // start intent
                startActivity(intent);
                break;
        }
    }


    @Override
    // get the result that the json from download url
    public void updateDownloadResult(String result) {

        Intent intent;
        // intent to QuizActivity
        intent = new Intent(this, QuizActivity.class);
        // put json to intent
        intent.putExtra("json", result);
        // start intent to QuizActivity
        startActivity(intent);
    }


}