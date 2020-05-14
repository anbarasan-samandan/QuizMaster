package com.droid.quizmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

public class Score_Card extends AppCompatActivity {

    private QuestionsModel[] questionModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score__card);

        Intent objIntent = getIntent();

        int[] answerChosenByUser = objIntent.getIntArrayExtra(QuizTemplate.SelectedAns);

        SharedPreferences shardPref = getApplicationContext().getSharedPreferences("Quiz", Context.MODE_PRIVATE);
        String quizQuestions = shardPref.getString(getString(R.string.QuizQuestions), "");

        questionModels = QuestionsModel.GetQuizQuestions(quizQuestions);

        int score = 0;

        for(int index = 0; index < questionModels.length; index++){
            QuestionsModel objQuestion = questionModels[index];

            if(objQuestion.getAnswerIndex() != answerChosenByUser[index])
                continue;

            score++;
        }

        showScore(score);

        for(int index =0; index < questionModels.length; index++) {
            createTextView(index, answerChosenByUser[index]);
        }
    }

    private void showScore(int score)
    {
        try {
            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

            TextView scoreView = new TextView(this);
            //scoreView.setPadding(10, 30, 10, 10);
            scoreView.setText("Score: " + score + "/" + questionModels.length);
            scoreView.setTextSize(30);

            gridLayout.addView(scoreView);
        }
        catch(Exception ex)
        {
            Log.e("showScore", ex.getMessage());
        }
    }

    private void createTextView(int currentIndex, int answerIndex)
    {
        try {
            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

            TextView correctAnswerTextView = new TextView(this);
            TextView answerChosenTextView = new TextView(this);

            GridLayout.Spec rowSpec = GridLayout.spec(currentIndex + 2);
            GridLayout.Spec colSpec_01 = GridLayout.spec(0);
            GridLayout.Spec colSpec_02 = GridLayout.spec(1);

            GridLayout.LayoutParams firstColumnLayoutParams = new GridLayout.LayoutParams(rowSpec, colSpec_01);
            firstColumnLayoutParams.setGravity(Gravity.LEFT);
            GridLayout.LayoutParams secondColumnLayoutParams = new GridLayout.LayoutParams(rowSpec, colSpec_02);
            secondColumnLayoutParams.setGravity(Gravity.LEFT);

            if(currentIndex == 0) {
                TextView colHeader01 = new TextView(this);
                colHeader01.setText(R.string.correctAnswer);
                colHeader01.setTextSize(18);
                colHeader01.setTypeface(null, Typeface.BOLD);

                TextView colHeader02 = new TextView(this);
                colHeader02.setText(R.string.yourAnswer);
                colHeader02.setTextSize(18);
                colHeader02.setTypeface(null, Typeface.BOLD);

                GridLayout.Spec rowHeaderIndex = GridLayout.spec(1);
                GridLayout.Spec columnHeaderIndex_01 = GridLayout.spec(0);
                GridLayout.Spec columnHeaderIndex_02 = GridLayout.spec(1);

                GridLayout.LayoutParams header01Params = new GridLayout.LayoutParams(rowHeaderIndex, columnHeaderIndex_01);
                header01Params.setGravity(Gravity.CENTER_HORIZONTAL);
                GridLayout.LayoutParams header02Params = new GridLayout.LayoutParams(rowHeaderIndex, columnHeaderIndex_02);
                header02Params.setGravity(Gravity.CENTER_HORIZONTAL);

                gridLayout.addView(colHeader01, header01Params);
                gridLayout.addView(colHeader02, header02Params);
            }

            int correctAnswerIndex = questionModels[currentIndex].getAnswerIndex();
            String correctAnswer = questionModels[currentIndex].getAnswerOptions()[correctAnswerIndex - 1] + "\r\n";
            correctAnswerTextView.setText(correctAnswer);
            correctAnswerTextView.canScrollHorizontally(1);
            correctAnswerTextView.setTextSize(12);
            //correctAnswerTextView.setPadding(0,0, 16, 16);

            String userAnswer = questionModels[currentIndex].getAnswerOptions()[answerIndex - 1] + "\r\n";
            answerChosenTextView.setText(userAnswer);
            answerChosenTextView.setTextSize(12);
            if(correctAnswerIndex == answerIndex)
                answerChosenTextView.setTextColor(Color.GREEN);
            else
                answerChosenTextView.setTextColor(Color.RED);

            //answerChosenTextView.setPadding(16, 0, 0, 16);

            gridLayout.addView(correctAnswerTextView, firstColumnLayoutParams);
            gridLayout.addView(answerChosenTextView, secondColumnLayoutParams);
        }
        catch(Exception ex)
        {
            Log.e("createTextView", ex.getMessage());
        }
    }
}
