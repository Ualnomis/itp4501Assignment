package com.example.itp4501assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements OnDownloadFinishListener {

    String json;
    List<QuestionItem> questionItems;
    private TextView tvQuestiion;
    private RadioButton answer1, answer2, answer3, answer4;
    int currentQuestion = 0;
    int correct = 0;
    int wrong = 0;
    MyAsyncTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        tvQuestiion = findViewById(R.id.tvQuestion);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        if (task == null || task.getStatus().equals(AsyncTask.Status.FINISHED)) {
            task = new MyAsyncTask();
            new MyAsyncTask(this).execute();
        }

        loadAllQuestions();;

        Collections.shuffle(questionItems);

        setQuestionScreen(currentQuestion);
    }

    @Override
    public void updateDownloadResult(String result) {
        json = result;
    }

    // make list with all questions
    private void loadAllQuestions() {
        questionItems = new ArrayList<>();
        String jsonStr = "{\"questions\": [{\"question\": \"11, 13, 17, 19, 23, 29, 31, 37, 41, ? \", \"answer\": 43}, {\"question\": \"11, 10, ?, 100, 1001, 1000, 10001\", \"answer\": 101}, {\"question\": \"20, 19, 17, ?, 10, 5\", \"answer\": 14}, {\"question\": \"9, 12, 11, 14, 13, ?, 15\", \"answer\": 16}, {\"question\": \"4, 6, 12, 14, 28, 30, ?\", \"answer\": 60}, {\"question\": \"36, 34, 30, 28, 24, ?\", \"answer\": 22}, {\"question\": \"1, 4, 27, 16, ?, 36, 343\", \"answer\": 125}, {\"question\": \"6, 11, 21, 36, 56, ? \", \"answer\": 81}, {\"question\": \"2, 3, 5, 7, 11, ?, 17\", \"answer\": 13}, {\"question\": \"2, 7, 14, 23, ?, 47\", \"answer\": 34}]}";
       try {
           JSONObject jsonObject = new JSONObject(jsonStr);
           JSONArray questions = jsonObject.getJSONArray("questions");
           for (int i = 0; i < questions.length(); i++) {
               JSONObject question = questions.getJSONObject(i);

               String questionString = question.getString("question");
               int correct = question.getInt("answer");

               questionItems.add(new QuestionItem(questionString, correct));
           }
        } catch (JSONException e) {
            e.printStackTrace();
       }
    }

    // set question to the screen
    private void setQuestionScreen(int number) {
        tvQuestiion.setText(questionItems.get(number).getQuestion());
        answer1.setText(questionItems.get(number).getAnswer1() + "");
        answer2.setText(questionItems.get(number).getAnswer2() + "");
        answer3.setText(questionItems.get(number).getAnswer3() + "");
        answer4.setText(questionItems.get(number).getAnswer4() + "");
    }

    public void onNextClick(View v) {
        if (answer1.isChecked()) {
            if (questionItems.get(currentQuestion).getAnswer1() == questionItems.get(currentQuestion).getCorrect()) {
                correct++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                wrong++;
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}