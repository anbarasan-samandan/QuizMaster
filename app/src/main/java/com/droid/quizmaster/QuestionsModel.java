package com.droid.quizmaster;


/**
 * Created by anbu on 12-08-2017.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.String;

public class QuestionsModel implements Serializable{
    private String question;
    private String[] answerOptions;
    private int answerIndex;
    private String subject;
    private String category;
    private String subcategory;

    public QuestionsModel(String paramSubject, String paramCategory, String paramSubCategory,
            String paramName, String[] paramOptions, int paramAnswerIndex)
    {
        this.subject = paramSubject;
        this.category = paramCategory;
        this.subcategory = paramSubCategory;
        this.question = paramName;
        this.answerOptions = paramOptions;
        this.answerIndex = paramAnswerIndex;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public String[] getAnswerOptions()
    {
        return this.answerOptions;
    }

    public  int getAnswerIndex()
    {
        return this.answerIndex;
    }

    public  String getSubject() {return this.subject; }

    public  String getCategory(){return  this.category;}

    public  String getSubcategory() {return this.subcategory;}

    private static QuestionsModel[] QUESTIONS_Quiz = {};

    public static QuestionsModel[] GetQuizQuestions(String quizQuestions)
    {
        try {

            Log.d("GetQuizQuestions","Questions");

            JSONArray quizArray = new JSONArray(quizQuestions);

            QUESTIONS_Quiz = new QuestionsModel[quizArray.length()];

            for (int iIndex = 0; iIndex < quizArray.length(); iIndex++) {

                JSONObject _jsonObject = quizArray.getJSONObject(iIndex);

                String _subject = _jsonObject.getString("subject");
                String _category = _jsonObject.getString("category");
                String _subCategory = _jsonObject.getString("subcategory");
                String _ask = _jsonObject.getString("ask");
                Integer _askIndex = Integer.valueOf(_jsonObject.getString("ansIndex"));
                JSONArray _jsonArrayAnswers = _jsonObject.getJSONArray("answers");

                String[] _answers = new String[_jsonArrayAnswers.length()];

                for (int iAnswerIndex = 0; iAnswerIndex < _jsonArrayAnswers.length();iAnswerIndex++) {
                    _answers[iAnswerIndex] = _jsonArrayAnswers.getString(iAnswerIndex);
                }
                QUESTIONS_Quiz[iIndex] = new QuestionsModel(
                        _subject, _category, _subCategory,
                        _ask, _answers, _askIndex);
            }
        }
        catch(JSONException e)
        {
            Log.d("read",e.getMessage());
            e.printStackTrace();
        }
        return QUESTIONS_Quiz;
    }
}