package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.itp4501assignment.animation.MyButtonInterpolator;
import com.example.itp4501assignment.database.DBHelper;
import com.example.itp4501assignment.asyncTask.MyAsyncTask;
import com.example.itp4501assignment.asyncTask.OnDownloadFinishListener;
import com.example.itp4501assignment.entity.QuestionItem;
import com.example.itp4501assignment.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements OnDownloadFinishListener {

    // variable dictionary
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
    int[] isCorrect;
    int[] yourAnswer;
    String json1;

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
        // set the number of correct question need to save
        isCorrect = new int[numOfQuestion];
        // set the number of answer need to save
        yourAnswer = new int[numOfQuestion];

//        // disable the button before the question is load
//        btnNext.setEnabled(false);
//        // set the button Next to Loading before the question is load
//        btnNext.setText("Loading...");


        // use ASyncTask to get the json from url
        // task = new MyAsyncTask();
        // new MyAsyncTask(this).execute();

        // get the json from intent
        Intent intent = getIntent();
        json1 = intent.getStringExtra("json");

        // load all the question from json url
        loadAllQuestion(json1);

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

    // action when btnNext click
    public void onNextClick(View v) {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        v.startAnimation(myAnim);

        if (answer1.isChecked()) {
            if (questionItems.get(currentQuestion).getAnswer1() == questionItems.get(currentQuestion).getCorrect()) {
                correct++;
                isCorrect[currentQuestion] = 1;

            } else {
                wrong++;
                isCorrect[currentQuestion] = 0;
            }
            yourAnswer[currentQuestion] = questionItems.get(currentQuestion).getAnswer1();
            updateQuestion();
        } else if (answer2.isChecked()) {
            if (questionItems.get(currentQuestion).getAnswer2() == questionItems.get(currentQuestion).getCorrect()) {
                correct++;
                isCorrect[currentQuestion] = 1;
            } else {
                wrong++;
                isCorrect[currentQuestion] = 0;
            }
            yourAnswer[currentQuestion] = questionItems.get(currentQuestion).getAnswer2();
            updateQuestion();
        } else if (answer3.isChecked()) {
            if (questionItems.get(currentQuestion).getAnswer3() == questionItems.get(currentQuestion).getCorrect()) {
                correct++;
                isCorrect[currentQuestion] = 1;
            } else {
                wrong++;
                isCorrect[currentQuestion] = 0;
            }
            yourAnswer[currentQuestion] = questionItems.get(currentQuestion).getAnswer3();
            updateQuestion();
        } else if (answer4.isChecked()) {
            if (questionItems.get(currentQuestion).getAnswer4() == questionItems.get(currentQuestion).getCorrect()) {
                correct++;
                isCorrect[currentQuestion] = 1;
            } else {
                wrong++;
                isCorrect[currentQuestion] = 0;
            }
            yourAnswer[currentQuestion] = questionItems.get(currentQuestion).getAnswer4();
             updateQuestion();
        }
    }

    // update the question after answer the previous question
    private void updateQuestion() {
        int size = numOfQuestion - 1;

        if (currentQuestion < size) {
            currentQuestion++;
            setQuestionScreen(currentQuestion);
            if (currentQuestion == size) {
                btnNext.setText("Finish");
            }
        } else {
            // get the finish time
            double finishTime = System.currentTimeMillis();
            // calculate the total play time
            double elapsedTime = (finishTime - startTime) / 1000;

            // store the data to database
            DBHelper dbHelper1 = new DBHelper(this, "quizDB", 2);


            // insert data to database
            for (int i = 0; i < numOfQuestion; i++) {
                String question = (questionItems.get(i).getQuestion());
                dbHelper1.addQuestionsRecord(question, yourAnswer[i], isCorrect[i]);
            }
            dbHelper1.addTestsLogRecord(elapsedTime, correct);

            // create a intent to a new activity
            Intent intent = new Intent(this, QuizFinish.class);
            intent.putExtra("playTime", elapsedTime);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);

            // start the activity
            startActivity(intent);
            finish();
        }
    }
}