package com.example.itp4501assignment;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> testNo, testDate, testTime, duration, correctCount;

    public CustomAdapter(Context context, ArrayList testNo, ArrayList testDate, ArrayList testTime, ArrayList duration, ArrayList correctCount) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTestNo.setText(String.valueOf(testNo.get(position)));
        holder.tvTestDate.setText(String.valueOf(testDate.get(position)));
        holder.tvTestTime.setText(String.valueOf(testTime.get(position)));
        holder.tvDuration.setText(String.valueOf(duration.get(position)));
        holder.tvCorrectCount.setText(String.valueOf(correctCount.get(position)));

    }

    @Override
    public int getItemCount() {
        return testNo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestNo, tvTestDate, tvTestTime, tvDuration, tvCorrectCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTestNo = itemView.findViewById(R.id.tvTestNo);
            tvTestDate = itemView.findViewById(R.id.tvTestDate);
            tvTestTime = itemView.findViewById(R.id.tvTestTime);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvCorrectCount = itemView.findViewById(R.id.tvCorrectCount);
        }
    }
}
