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
                new MyAsyncTask(this).execute();

                break;

            case R.id.btnRecord:

                intent = new Intent(this, QuizRecord.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void updateDownloadResult(String result) {
        Intent intent;
        intent = new Intent(this, QuizActivity.class);
        intent.putExtra("json", result);
        startActivity(intent);
    }


}