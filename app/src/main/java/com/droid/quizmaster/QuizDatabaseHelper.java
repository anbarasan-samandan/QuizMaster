package com.droid.quizmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anbu on 24-03-2018.
 */

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "QuizMasterDB.db";
    private static final int DB_VERSION = 1;

    QuizDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE QUIZ_SUBJECT ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "CODE TEXT NOT NULL UNIQUE,"
                + "DESCRIPTION TEXT NOT NULL);");

        db.execSQL("CREATE TABLE QUIZ_CATEGORY ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "SUBJECT_CODE TEXT NOT NULL,"
                + "CODE TEXT NOT NULL UNIQUE,"
                + "DESCRIPTION TEXT NOT NULL);");

        db.execSQL("CREATE TABLE QUIZ_SUBCATEGORY ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "SUBJECT_CODE TEXT NOT NULL,"
                + "CATEGORY_CODE TEXT NOT NULL"
                + "CODE TEXT NOT NULL UNIQUE,"
                + "DESCRIPTION NOT NULL);");

        db.execSQL("CREATE TABLE QUIZ_QUESTIONS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "SUBJECT_CODE TEXT NOT NULL,"
                + "CATEGORY_CODE TEXT NOT NULL,"
                + "SUBCATEGORY_CODE TEXT NOT NULL,"
                + "QUESTION_TYPE TEXT NOT NULL,"
                + "ASK TEXT TEXT,"
                + "ASK_IMAGE BLOB,"
                + "ANSWER_INDEX INTEGER NOT NULL);"
                );

        db.execSQL("CREATE TABLE QUIZ_ANSWER_TEXT_OPTIONS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "QUESTION_INDEX INTEGER NOT NULL,"
                + "ANSWER_OPTION TEXT NOT NULL);");

        db.execSQL("CREATE TABLE QUIZ_ANSWER_IMAGE_OPTIONS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "QUESTION_INDEX INTEGER NOT NULL,"
                + "ANSWER_OPTION BLOB NOT NULL);");

        InsertSubject(db, "ENG", "English");
        InsertCategory(db, "ENG", "GRAM", "Grammar");
        InsertSubCategory(db, "ENG", "GRAM", "PREP", "Prepositions" );
        long questionIndex = InsertTextQuestion(db, "ENG", "GRAM","PREP", "The book is ______ the table", 3);
        InsertTextAnswers(db, questionIndex, "with");
        InsertTextAnswers(db, questionIndex, "for");
        InsertTextAnswers(db, questionIndex, "on");
        InsertTextAnswers(db, questionIndex, "between");
    }

    public static void InsertSubject(SQLiteDatabase db, String code, String description)
    {
        ContentValues subjectValues = new ContentValues();
        subjectValues.put("CODE", code);
        subjectValues.put("DESCRIPTION", description);
        db.insert("QUIZ_SUBJECT", null, subjectValues);
    }

    public static void InsertCategory(SQLiteDatabase db, String subjectCode, String code, String description)
    {
        ContentValues categoryValues = new ContentValues();
        categoryValues.put("SUBJECT_CODE", subjectCode);
        categoryValues.put("CODE", code);
        categoryValues.put("DESCRIPTION", description);
        db.insert("QUIZ_CATEGORY", null, categoryValues);
    }

    private static void InsertSubCategory(SQLiteDatabase db, String subjectCode, String categoryCode, String code, String description)
    {
        ContentValues subCategoryValues = new ContentValues();
        subCategoryValues.put("SUBJECT_CODE", subjectCode);
        subCategoryValues.put("CATEGORY_CODE", categoryCode);
        subCategoryValues.put("CODE", code);
        subCategoryValues.put("DESCRIPTION", description);

        db.insert("QUIZ_SUBCATEGORY", null, subCategoryValues);
    }

    private static long InsertTextQuestion(SQLiteDatabase db, String subjectCode, String categoryCode, String strSubcategoryCode,
                                       String ask, int answerIndex)
    {
        ContentValues questionValues = new ContentValues();
        questionValues.put("SUBJECT_CODE", subjectCode);
        questionValues.put("CATEGORY_CODE", categoryCode);
        questionValues.put("SUBCATEGORY_CODE", strSubcategoryCode);
        questionValues.put("QUESTION_TYPE", "TEXTUAL");
        questionValues.put("ASK_TEXT", ask);
        questionValues.put("ANSWER_INDEX", answerIndex);

        return db.insert("QUIZ_QUESTIONS", null, questionValues);
    }

    private static void InsertTextAnswers(SQLiteDatabase db, long questionIndex, String answerOption)
    {
        db.execSQL("CREATE TABLE QUIZ_ANSWER_TEXT_OPTIONS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "QUESTION_INDEX INTEGER NOT NULL,"
                + "ANSWER_OPTION TEXT NOT NULL);");

        ContentValues answerOptionValues = new ContentValues();
        answerOptionValues.put("QUESTION_INDEX", questionIndex);
        answerOptionValues.put("ANSWER_OPTION", answerOption);

        db.insert("QUIZ_ANSWER_TEXT_OPTIONS", null, answerOptionValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
