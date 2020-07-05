package com.example.itp4501assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements OnDownloadFinishListener {

    String json;
    List<QuestionItem> questionItems;
    private TextView tvQuestion, tvNoOfQuestion, tvCorrect;
    private RadioButton answer1, answer2, answer3, answer4;
    int currentQuestion = 0;
    int correct = 0;
    int wrong = 0;
    int numOfQuestion = 0;
    private double startTime = 0;
    MyAsyncTask task = null;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);
        tvQuestion = findViewById(R.id.tvQuestion);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        btnNext = findViewById(R.id.btnNext);
        tvNoOfQuestion = findViewById(R.id.tvNoOfQuestion);
        tvCorrect = findViewById(R.id.tvCorrect);

        // set the number of question need to get
        numOfQuestion = 5;

        // disable the button before the question is load
        btnNext.setEnabled(false);
        // set the button Next to Loading before the question is load
        btnNext.setText("Loading...");

        // use ASyncTask to get the json from url
        task = new MyAsyncTask();
        new MyAsyncTask(this).execute();
    }

    @Override
    public void updateDownloadResult(String result) {
        // load all the question from json url
        loadAllQuestion(result);
        // shuffle the question order
        Collections.shuffle(questionItems);
        // set the question to screen
        setQuestionScreen(currentQuestion);

        // enable the button after the questions are load
        btnNext.setText("Next");
        btnNext.setEnabled(true);

        // initial the start time
        startTime = System.currentTimeMillis();
    }

    private void loadAllQuestion(String json) {
        questionItems = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray questions = jsonObject.getJSONArray("questions");
            for (int i = 0; i < questions.length(); i++) {
                JSONObject question = questions.getJSONObject(i);

                String questionString = question.getString("question");
                int answer = question.getInt("answer");

                questionItems.add(new QuestionItem(questionString, answer));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // set question to the screen
    private void setQuestionScreen(int number) {
        tvQuestion.setText(questionItems.get(number).getQuestion());
        answer1.setText(questionItems.get(number).getAnswer1() + "");
        answer2.setText(questionItems.get(number).getAnswer2() + "");
        answer3.setText(questionItems.get(number).getAnswer3() + "");
        answer4.setText(questionItems.get(number).getAnswer4() + "");
        tvCorrect.setText("Correct: " + correct + " / " + numOfQuestion);
        tvNoOfQuestion.setText("No of Question: " + (number + 1) + " / " + numOfQuestion);
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

            updateQuestion();
        } else if (answer2.isChecked()) {
            if (questionItems.get(currentQuestion).getAnswer2() == questionItems.get(currentQuestion).getCorrect()) {
                correct++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                wrong++;
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }

            updateQuestion();
        } else if (answer3.isChecked()) {
            if (questionItems.get(currentQuestion).getAnswer3() == questionItems.get(currentQuestion).getCorrect()) {
                correct++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                wrong++;
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }

            updateQuestion();
        } else if (answer4.isChecked()) {
            if (questionItems.get(currentQuestion).getAnswer4() == questionItems.get(currentQuestion).getCorrect()) {
                correct++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                wrong++;
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }

             updateQuestion();
        }
    }
    // update the question after answer the previous question
    private void updateQuestion() {
        int size = numOfQuestion - 1;
        if (currentQuestion < size) {
            currentQuestion++;
            setQuestionScreen(currentQuestion);
        } else {
            double finishTime = System.currentTimeMillis();
            double elapsedTime = (finishTime - startTime) / 1000;
            Intent intent = new Intent(this, QuizFinish.class);
            intent.putExtra("playTime", elapsedTime);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);
            startActivity(intent);
            finish();
        }
    }

}