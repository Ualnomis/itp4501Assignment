package com.example.itp4501assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnStart:
                System.out.println("Start the quiz");
                intent = new Intent(this, QuizActivity.class);
                startActivity(intent);
                break;

            case R.id.btnRecord:
                intent = new Intent(this, QuizRecord.class);
                startActivity(intent);
                break;
        }
    }
}