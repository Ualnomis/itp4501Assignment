package com.example.itp4501assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionItem {
    String question;
    List<Integer> answerList = new ArrayList<>();
    Integer[] answerArray = new Integer[4];
    int answer1;
    int answer2;
    int answer3;
    int answer4;
    int correct;

    public QuestionItem(String question, int correct) {
        this.question = question;
        this.correct = correct;

        answerList.add(correct);
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            answerList.add(random.nextInt(500 - 1 + 1) + 1);
        }
        Collections.shuffle(answerList);
        answerList.toArray(answerArray);
        this.answer1 = answerArray[0];
        this.answer2 = answerArray[1];
        this.answer3 = answerArray[2];
        this.answer4 = answerArray[3];
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer1() {
        return answer1;
    }

    public int getAnswer2() {
        return answer2;
    }

    public int getAnswer3() {
        return answer3;
    }

    public int getAnswer4() {
        return answer4;
    }

    public int getCorrect() {
        return correct;
    }

    private int[] arrayAddToEnd(int[] array, int value) {
        int[] result = Arrays.copyOf(array, array.length + 1);
        result[result.length - 1] = value;
        return result;
    }
}
