package com.example.itp4501assignment.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/*
 * store all the question get from json
 */
public class QuestionItem {
    // variable dictionary
    String question; // question
    List<Integer> answerList = new ArrayList<>(); // answer arraylist
    Integer[] answerArray = new Integer[4]; // answer array
    int answer1;
    int answer2;
    int answer3;
    int answer4;
    int correct; // correct answer

    public QuestionItem(String question, int correct) {
        // set question and correct answer
        this.question = question;
        this.correct = correct;
        answerList.add(correct);

        // generate the random number from 1 to 500
//        Random random = new Random();
//        for (int i = 0; i < 3; i++) {
//            answerList.add(random.nextInt((correct + 4) - (correct - 4) + 1) + (correct - 4));
//        }

        // create a arraylist
        ArrayList<Integer> list = new ArrayList<Integer>();

        // generate number from 1 to (correct + 4)
        for (int i= 1; i<(correct + 4); i++) {
            // if i is not equal to correct
            if (i != correct) {
                // add the number to list
                list.add(new Integer(i));
            }
        }

        // shuffle the data in list
        Collections.shuffle(list);

        // select first three item from shuffled list
        for (int i=0; i<3; i++) {
            System.out.println(list.get(i));
            // add the first three item to answerList
            answerList.add(list.get(i));
        }

        // shuffle answerList
        Collections.shuffle(answerList);
        // answerList to array
        answerList.toArray(answerArray);
        // put answerList data to variable
        this.answer1 = answerArray[0];
        this.answer2 = answerArray[1];
        this.answer3 = answerArray[2];
        this.answer4 = answerArray[3];
    }

    // getter
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

}
