package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.itp4501assignment.database.DBHelper;
import com.example.itp4501assignment.R;

import java.util.ArrayList;

public class ViewQuizDetail extends AppCompatActivity {

    RecyclerView recyclerView;
    DBHelper dbHelper;
    ArrayList<String> questionNo, question, yourAnswer, isCorrect;
    int testNo = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz_detail);
        recyclerView = findViewById(R.id.recyclerViewQuestionsLog);

        dbHelper = new DBHelper(this, "quizDB", 2);
        questionNo = new ArrayList<>();
        question = new ArrayList<>();
        yourAnswer = new ArrayList<>();
        isCorrect = new ArrayList<>();

        testNo = getIntent().getIntExtra("testNo", 0);
        storeQuestionsLogInArrays();

    }

    private void storeQuestionsLogInArrays() {
        Cursor cursor = dbHelper.readQuestionsLogData(testNo);
        System.out.println(cursor);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                questionNo.add(cursor.getString(cursor.getColumnIndex("questionNo")));
                question.add(cursor.getString(cursor.getColumnIndex("question")));
                yourAnswer.add(cursor.getString(cursor.getColumnIndex("yourAnswer")));
                isCorrect.add(cursor.getString(cursor.getColumnIndex("isCorrect")));

            }
        }
    }

}