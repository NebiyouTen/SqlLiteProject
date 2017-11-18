//On my honor, as a Carnegie-Mellon Rwanda student,
// I have neither given nor received unauthorized
// assistance on this work.
package com.example.nyismaw.sqlliteproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import Database.DbOperations;
import Model.Courses;

import static android.database.sqlite.SQLiteDatabase.OPEN_READWRITE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner spinner = findViewById(R.id.courseSpinner);
        DbOperations dbOperations = new DbOperations(this);
        List<Courses> courses = dbOperations.getCourses();
        List<String> strings = new ArrayList();
        for (Courses c : courses) {
            strings.add(c.getCourseName());
        }

        SQLiteDatabase mydatabase = openOrCreateDatabase("StudentManagment", OPEN_READWRITE, null);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("studentId", "3");
        contentValues2.put("courseId", "1");
        contentValues2.put("quizName", "Quiz Four");
        contentValues2.put("result", 61);
        mydatabase.insert("QuizResults", null, contentValues2);
        contentValues2 = new ContentValues();
        contentValues2.put("studentId", "3");
        contentValues2.put("courseId", "1");
        contentValues2.put("quizName", "Quiz Three");
        contentValues2.put("result", 69.7);
        mydatabase.insert("QuizResults", null, contentValues2);
        contentValues2 = new ContentValues();
        contentValues2.put("studentId", "3");
        contentValues2.put("courseId", "1");
        contentValues2.put("quizName", "Quiz Two");
        contentValues2.put("result", 88.7);
        mydatabase.insert("QuizResults", null, contentValues2);

        contentValues2 = new ContentValues();
        contentValues2.put("studentId", "3");
        contentValues2.put("courseId", "1");
        contentValues2.put("quizName", "Quiz One");
        contentValues2.put("result", 98.7);
        mydatabase.insert("QuizResults", null, contentValues2);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {

                Fragment f = new CourseInformation((String) spinner.getSelectedItem());
                FragmentManager fragman = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragman.beginTransaction();
                transaction.replace(R.id.fragment, f);
                transaction.commitAllowingStateLoss();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
}
