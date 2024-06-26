package com.example.itp4501assignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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


/*
 * used for quiz the json question
 */
public class QuizActivity extends AppCompatActivity implements OnDownloadFinishListener {

    // variable dictionary
    String json;
    List<QuestionItem> questionItems;
    private TextView tvQuestion, tvNoOfQuestion, tvCorrect;
    private RadioButton answer1, answer2, answer3, answer4;
    int currentQuestion = 0;
    int correct = 0;
    int wrong = 0;
    public static final int NUMOFQUESTION = 5; // the number of question need to display (will use in BarChart class
    private double startTime = 0;
    MyAsyncTask task = null;
    Button btnNext;
    int[] isCorrect;
    int[] yourAnswer;
    String json1;
    ImageView[] ivQ;

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



        // set the number of correct question need to save
        isCorrect = new int[NUMOFQUESTION];
        // set the number of answer need to save
        yourAnswer = new int[NUMOFQUESTION];

        ivQ = new ImageView[NUMOFQUESTION];
        ivQ[0] = findViewById(R.id.ivQ1);
        ivQ[1] = findViewById(R.id.ivQ2);
        ivQ[2] = findViewById(R.id.ivQ3);
        ivQ[3] = findViewById(R.id.ivQ4);
        ivQ[4] = findViewById(R.id.ivQ5);

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

    // load all question from json
    private void loadAllQuestion(String json) {
        // create arraylist to store question and answer
        questionItems = new ArrayList<>();

        // try to get data from json
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray questions = jsonObject.getJSONArray("questions");
            // put the json array to questionItem arraylist
            for (int i = 0; i < questions.length(); i++) {
                JSONObject question = questions.getJSONObject(i);

                String questionString = question.getString("question");
                int answer = question.getInt("answer");

                questionItems.add(new QuestionItem(questionString, answer));
            }
        } catch (JSONException e) { // throw the error when exception occur
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
        tvCorrect.setText("Correct: " + correct + " / " + NUMOFQUESTION);
        tvNoOfQuestion.setText("No of Question: " + (number + 1) + " / " + NUMOFQUESTION);
    }

    // action when btnNext click
    public void onNextClick(View v) {
        // animation when the button click
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.image_click);
        MyButtonInterpolator interpolator = new MyButtonInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        v.startAnimation(myAnim);

        // check the answer
        if (answer1.isChecked()) { // check if answer 1 is checked

            if (questionItems.get(currentQuestion).getAnswer1() == questionItems.get(currentQuestion).getCorrect()) { // if answer 1 is correct
                correct++;
                ivQ[currentQuestion].setImageResource(R.drawable.tick);
                isCorrect[currentQuestion] = 1;
            } else { // if answer 1 is wrong
                wrong++;
                ivQ[currentQuestion].setImageResource(R.drawable.cross);
                isCorrect[currentQuestion] = 0;
            }
            yourAnswer[currentQuestion] = questionItems.get(currentQuestion).getAnswer1();
            // update the question only when there are radio button checked
            updateQuestion();
        } else if (answer2.isChecked()) { // check if answer 2 is checked
            if (questionItems.get(currentQuestion).getAnswer2() == questionItems.get(currentQuestion).getCorrect()) { // if answer 2 is correct
                correct++;
                ivQ[currentQuestion].setImageResource(R.drawable.tick);
                isCorrect[currentQuestion] = 1;
            } else { // if answer 2 is wrong
                wrong++;
                ivQ[currentQuestion].setImageResource(R.drawable.cross);
                isCorrect[currentQuestion] = 0;
            }
            yourAnswer[currentQuestion] = questionItems.get(currentQuestion).getAnswer2();
            // update the question only when there are radio button checked
            updateQuestion();
        } else if (answer3.isChecked()) { // check if answer 3 is checked
            if (questionItems.get(currentQuestion).getAnswer3() == questionItems.get(currentQuestion).getCorrect()) { // if answer 3 is correct
                correct++;
                ivQ[currentQuestion].setImageResource(R.drawable.tick);
                isCorrect[currentQuestion] = 1;
            } else { // if answer 3 is wrong
                wrong++;
                ivQ[currentQuestion].setImageResource(R.drawable.cross);
                isCorrect[currentQuestion] = 0;
            }
            yourAnswer[currentQuestion] = questionItems.get(currentQuestion).getAnswer3();
            // update the question only when there are radio button checked
            updateQuestion();
        } else if (answer4.isChecked()) { // check if answer 4 is checked
            if (questionItems.get(currentQuestion).getAnswer4() == questionItems.get(currentQuestion).getCorrect()) { // if answer 4 is correct
                correct++;
                ivQ[currentQuestion].setImageResource(R.drawable.tick);
                isCorrect[currentQuestion] = 1;
            } else { // if answer 4 is wrong
                wrong++;
                ivQ[currentQuestion].setImageResource(R.drawable.cross);
                isCorrect[currentQuestion] = 0;
            }
            yourAnswer[currentQuestion] = questionItems.get(currentQuestion).getAnswer4();
            // update the question only when there are radio button checked
            updateQuestion();
        } else {
            Toast.makeText(this, "Please select the answer!!!", Toast.LENGTH_LONG).show();
        }

    }

    // update the question after answer the previous question
    private void updateQuestion() {
        // variable dictionary
        int size = NUMOFQUESTION - 1; // the max index of the questionItems arraylist

        if (currentQuestion < size) { // if not the last question
            currentQuestion++;
            setQuestionScreen(currentQuestion);
            if (currentQuestion == size) { // if is the last question
                btnNext.setText("Finish");
            }
            unchecked();
        } else {
            // get the finish time
            double finishTime = System.currentTimeMillis();
            // calculate the total play time
            double elapsedTime = (finishTime - startTime) / 1000;

            // store the data to database
            DBHelper dbHelper1 = new DBHelper(this, "quizDB", 2);


            // insert data to database
            dbHelper1.addTestsLogRecord(elapsedTime, correct);

            // loop all question
            for (int i = 0; i < NUMOFQUESTION; i++) {
                // get teh question from questionItem arraylist
                String question = (questionItems.get(i).getQuestion());
                // store the data to database
                dbHelper1.addQuestionsRecord(question, yourAnswer[i], isCorrect[i]);
            }


            // create a intent to a new activity
            Intent intent = new Intent(this, QuizFinish.class);
            // put the variable that next intent required
            intent.putExtra("playTime", elapsedTime);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);

            // start the activity
            startActivity(intent);
            finish();
        }
    }
    // uncheck all the radio button
    public void unchecked()
    {
        RadioGroup x=findViewById(R.id.answerGroup);
        x.clearCheck();
    }
}