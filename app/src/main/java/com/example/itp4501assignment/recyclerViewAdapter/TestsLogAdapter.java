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

import java.util.ArrayList;

public class TestsLogAdapter extends RecyclerView.Adapter<TestsLogAdapter.MyViewHolder> {

    // variable dictionary
    private Context context; // set Context
    private ArrayList<String> testNo, testDate, testTime, duration, correctCount; // arrayList of the testsLog table column

    public TestsLogAdapter(Context context, ArrayList testNo, ArrayList testDate, ArrayList testTime, ArrayList duration, ArrayList correctCount) {
        this.context = context;
        this.testNo = testNo;
        this.testDate = testDate;
        this.testTime = testTime;
        this.duration = duration;
        this.correctCount = correctCount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.testslog_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    // put record data to TextView
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvTestNo.setText(String.valueOf(testNo.get(position)));
        holder.tvTestDate.setText(String.valueOf(testDate.get(position)));
        holder.tvTestTime.setText(String.valueOf(testTime.get(position)));
        holder.tvDuration.setText(String.valueOf(duration.get(position)) + "s");
        holder.tvCorrectCount.setText("Correct: " + String.valueOf(correctCount.get(position)));
        holder.testsLogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewQuizDetail.class);
                intent.putExtra("testNo", Integer.valueOf(testNo.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    // get how many record in TestsLog
    public int getItemCount() {
        return testNo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestNo, tvTestDate, tvTestTime, tvDuration, tvCorrectCount;
        LinearLayout testsLogLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTestNo = itemView.findViewById(R.id.tvTestNo);
            tvTestDate = itemView.findViewById(R.id.tvTestDate);
            tvTestTime = itemView.findViewById(R.id.tvTestTime);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvCorrectCount = itemView.findViewById(R.id.tvCorrectCount);
            testsLogLayout = itemView.findViewById(R.id.testsLogView);
        }
    }


}
