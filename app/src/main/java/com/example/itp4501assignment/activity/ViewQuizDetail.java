package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.itp4501assignment.database.DBHelper;
import com.example.itp4501assignment.R;

import java.util.ArrayList;

public class ViewQuizDetail extends AppCompatActivity {

    RecyclerView recyclerView;
    DBHelper dbHelper;
    ArrayList<String> questionNo, question, yourAnswer, isCorrect;

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
    }

    private void storeQuestionsLogInArrays() {

    }

}