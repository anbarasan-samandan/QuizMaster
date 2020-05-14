package com.droid.quizmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class QuizTemplate extends AppCompatActivity {

    public static final String SelectedAns = "Selected Answers";
    int numberOfQuestions;
    int currentQuestionIndex;
    int[] answerChosen;
    QuestionsModel[] questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_template);

        SharedPreferences shardPref = getApplicationContext().getSharedPreferences("Quiz", Context.MODE_PRIVATE);
        String quizQuestions = shardPref.getString(getString(R.string.QuizQuestions), "");

        questions = QuestionsModel.GetQuizQuestions(quizQuestions);

        numberOfQuestions = questions.length;
        answerChosen = new int[numberOfQuestions];

        QuestionsModel firstQuestion = questions[0];
        initializeOptions(firstQuestion);
    }

    public void showScore() {
        final Context objContext = this;
        Intent objIntent = new Intent(objContext, Score_Card.class);
        objIntent.putExtra(SelectedAns, answerChosen);

        startActivity(objIntent);
    }

    public void onButtonClicked(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.previousButton:
                answerChosen[currentQuestionIndex] = 1;
                currentQuestionIndex--;
                break;
            case R.id.nextButton:
                currentQuestionIndex++;
                break;
            case R.id.submitButton:
                showScore();
                break;
        }
        enableDisableButton();
        initializeOptions(questions[currentQuestionIndex]);
    }

    private void enableDisableButton() {
        Button btnPrevious = (Button) findViewById(R.id.previousButton);
        Button btnNext = (Button) findViewById(R.id.nextButton);
        Button btnSubmit = (Button) findViewById(R.id.submitButton);

        btnPrevious.setEnabled(true);
        btnNext.setEnabled(true);

        if (currentQuestionIndex == 0) {
            btnPrevious.setEnabled(false);
        } else if (currentQuestionIndex == (numberOfQuestions - 1)) {
            btnSubmit.setEnabled(true);
            btnNext.setEnabled(false);
        }
    }

    public void onAnswerChosen(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.option_01:
                answerChosen[currentQuestionIndex] = 1;
                break;
            case R.id.option_02:
                answerChosen[currentQuestionIndex] = 2;
                break;
            case R.id.option_03:
                answerChosen[currentQuestionIndex] = 3;
                break;
            case R.id.option_04:
                answerChosen[currentQuestionIndex] = 4;
                break;
        }

        Button selectedButton = (Button) findViewById(id);
        int correctAnswerIndex = questions[currentQuestionIndex].getAnswerIndex();
        if(correctAnswerIndex == answerChosen[currentQuestionIndex])
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.CorrectAnswer));
        else {
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.WrongAnswer));

            String buttonName = "option_0" + correctAnswerIndex;
            int buttonId = getResources().getIdentifier(buttonName, "id", getPackageName());
            Button correctAnswerButton = (Button) findViewById(buttonId);
            correctAnswerButton.setBackgroundColor(ContextCompat.getColor(this, R.color.CorrectAnswer));
        }
    }


    private void initializeOptions(QuestionsModel paramQuestionOptions)
    {
        /*Sanity check for null reference...*/
        if(paramQuestionOptions == null)
            return;

        Log.d("Initialize", paramQuestionOptions.getQuestion());

        /*
        if(answerChosen[currentQuestionIndex] <=0)
        {
            RadioGroup group = (RadioGroup) findViewById(R.id.options_group);
            group.clearCheck();
            return;
        }*/

        TextView textView = (TextView) findViewById(R.id.txtViewQuestion);
        textView.setText(paramQuestionOptions.getQuestion());

        Button option1 = (Button) findViewById(R.id.option_01);
        option1.setBackgroundResource(android.R.drawable.btn_default);
        //option1.getBackground().clearColorFilter();
        option1.setText(paramQuestionOptions.getAnswerOptions()[0]);

        Button option2 = (Button) findViewById((R.id.option_02));
        option2.setBackgroundResource(android.R.drawable.btn_default);
        option2.setText((paramQuestionOptions.getAnswerOptions()[1]));

        Button option3 = (Button) findViewById((R.id.option_03));
        option3.setBackgroundResource(android.R.drawable.btn_default);
        option3.setText((paramQuestionOptions.getAnswerOptions()[2]));

        Button option4 = (Button) findViewById((R.id.option_04));
        option4.setBackgroundResource(android.R.drawable.btn_default);
        option4.setText((paramQuestionOptions.getAnswerOptions()[3]));
    }
}
