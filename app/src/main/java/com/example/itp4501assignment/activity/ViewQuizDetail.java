package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.itp4501assignment.database.DBHelper;
import com.example.itp4501assignment.R;
import com.example.itp4501assignment.recyclerViewAdapter.QuestionsLogAdapter;
import com.example.itp4501assignment.recyclerViewAdapter.TestsLogAdapter;

import java.util.ArrayList;

/*
 * used to display the detail information of each test
 * display questions, answers and your corresponding answers and is correct or not
 */
public class ViewQuizDetail extends AppCompatActivity {

    // variable
    RecyclerView recyclerView;
    DBHelper dbHelper;
    ArrayList<String> questionNo, question, yourAnswer, isCorrect;
    private int testNo;
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    QuestionsLogAdapter questionsLogAdapter;

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
        questionsLogAdapter = new QuestionsLogAdapter(this, questionNo, question, yourAnswer, isCorrect);
        recyclerView.setAdapter(questionsLogAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void storeQuestionsLogInArrays() {
        Cursor cursor = dbHelper.readQuestionsLogData(testNo);
        System.out.println(testNo);
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