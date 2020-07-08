package com.example.itp4501assignment.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itp4501assignment.activity.ViewQuizDetail;
import com.example.itp4501assignment.R;

import java.text.BreakIterator;
import java.util.ArrayList;

public class QuestionsLogAdapter extends RecyclerView.Adapter<QuestionsLogAdapter.MyViewHolder> {

    // variable dictionary
    private Context context; // set Context
    private ArrayList<String> questionNo, question, yourAnswer, isCorrect; // arrayList of the testsLog table column

    public QuestionsLogAdapter(Context context, ArrayList questionNo, ArrayList question, ArrayList yourAnswer, ArrayList isCorrect) {
        this.context = context;
        this.questionNo = questionNo;
        this.question = question;
        this.yourAnswer = yourAnswer;
        this.isCorrect = isCorrect;
    }

    @NonNull
    @Override
    public QuestionsLogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.questionslog_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsLogAdapter.MyViewHolder holder, final int position) {
        holder.tvQuestionNo.setText(String.valueOf(questionNo.get(position)));
        holder.tvQuestion.setText(String.valueOf(question.get(position)));
        holder.tvYourAnswer.setText(String.valueOf(yourAnswer.get(position)));
        holder.tvIsCorrect.setText(String.valueOf(isCorrect.get(position)));
    }

    @Override
    public int getItemCount() {
        return questionNo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNo, tvQuestion, tvYourAnswer, tvIsCorrect;
        LinearLayout questionsLogLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNo = itemView.findViewById(R.id.tvQuestionNo);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvYourAnswer = itemView.findViewById(R.id.tvYourAnswer);
            tvIsCorrect = itemView.findViewById(R.id.tvIsCorrect);
            questionsLogLayout = itemView.findViewById(R.id.questionsLogView);
        }
    }
}
