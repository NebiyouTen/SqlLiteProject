// On my honor, as a Carnegie-Mellon Rwanda student,
// I have neither given nor received unauthorized
// assistance on this work.

package com.example.nyismaw.sqlliteproject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.DbOperations;
import Model.QuizResults;


public class CourseInformation extends Fragment {
    public CourseInformation() {
        // Required empty public constructor
    }

    public CourseInformation(String courseName) {
        this.courseName = courseName;
        Log.e("best tag", "Fragment Created with " + courseName);
    }

    String courseName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_information, container, false);
        TableLayout tableLayout = view.findViewById(R.id.studentTable);
        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);


        DbOperations dbOperations = new DbOperations(view.getContext());
        Cursor resultsByCourseName = dbOperations.getStudentsByCourse(courseName);
        List<QuizResults> quizesResults = dbOperations.getCourseQuizes(courseName);
        List<String> quizes= new ArrayList();
        for(QuizResults q: quizesResults)
                quizes.add(q.getQuizName());
        TableRow tableRowHeader = new TableRow(view.getContext());
        tableRowHeader.setBackground(border);
        tableRowHeader.setBackgroundColor(Color.DKGRAY);
        tableRowHeader.setPadding(2, 2, 2, 2); //Border between rows

        TextView header = new TextView(view.getContext());
        header.setText("Student Name");
        header.setTextColor(Color.WHITE);
        tableRowHeader.addView(header);
        header.setTextSize(9);
        header = new TextView(view.getContext());
        header.setText("  Id    ");

        header.setTextColor(Color.WHITE);
        header.setTextSize(9);
        tableRowHeader.addView(header);
        for (String q : quizes) {
            header = new TextView(view.getContext());
            header.setText(q + "      ");
            tableRowHeader.addView(header);

            header.setTextColor(Color.WHITE);
            header.setTextSize(9);
        }
        tableLayout.addView(tableRowHeader);
        if (resultsByCourseName.moveToFirst()) {
            do {
                String studentID = resultsByCourseName.getString(0);
                TableRow tableRow = new TableRow(view.getContext());
                tableRow.setBackground(border);
                tableRow.setBackgroundColor(Color.LTGRAY);
                tableRow.setPadding(4, 4, 2, 2); //Border between rows
                Cursor studentInfo = dbOperations.getStudentsByID(studentID);
                if (studentInfo.moveToFirst()) {
                    String name = studentInfo.getString(studentInfo.getColumnIndex("firstName")) + " ";
                    name += studentInfo.getString(studentInfo.getColumnIndex("lastName"));
                    TextView textView = new TextView(view.getContext());
                    textView.setText(" " + name);
                    textView.setTextSize(9);
                    tableRow.addView(textView);
                    textView = new TextView(view.getContext());

                    textView.setTextSize(8);
                    textView.setText(studentInfo.getString(studentInfo.getColumnIndex("studentIdNumber")));

                    textView.setTextSize(8);
                    tableRow.addView(textView);
                    for (int j = 0; j < quizes.size(); j++) {
                        String quizResult = dbOperations.getQuizResult(studentID, courseName, quizes.get(j)).getResut();
                        textView = new TextView(view.getContext());
                        textView.setText(" " + quizResult);

                        textView.setTextSize(9);
                        tableRow.addView(textView);

                    }
                }



                tableLayout.addView(tableRow);
            } while (resultsByCourseName.moveToNext());

            TableRow stat = new TableRow(view.getContext());
            stat.setBackground(border);
            stat.setBackgroundColor(Color.GRAY);
            stat.setPadding(2, 2, 2, 2); //Border between rows
            TextView textView = new TextView(view.getContext());
            textView.setText(" High Score   ");

            textView.setTextSize(9);
            stat.addView(textView);

            textView = new TextView(view.getContext());
            textView.setText("          ");
            stat.addView(textView);
            for (String quiz : quizes) {
                textView = new TextView(view.getContext());
                textView.setText(dbOperations.maxQuizResult(courseName,quiz));
                stat.addView(textView);
                textView.setTextSize(8);

            }
            tableLayout.addView(stat);



             stat = new TableRow(view.getContext());
            stat.setBackground(border);
            stat.setBackgroundColor(Color.GRAY);
            stat.setPadding(2, 2, 2, 2); //Border between rows
             textView = new TextView(view.getContext());
            textView.setText(" Low Score   ");

            textView.setTextSize(8);
            stat.addView(textView);

            textView = new TextView(view.getContext());
            textView.setText("          ");
            stat.addView(textView);
            for (String quiz : quizes) {
                textView = new TextView(view.getContext());
                textView.setText(dbOperations.minQuizResult(courseName,quiz));
                stat.addView(textView);
                textView.setTextSize(9);
            }
            tableLayout.addView(stat);



             stat = new TableRow(view.getContext());
            stat.setBackground(border);
            stat.setBackgroundColor(Color.GRAY);
            stat.setPadding(2, 2, 2, 2); //Border between rows
             textView = new TextView(view.getContext());
            textView.setText(" Average Score   ");

            textView.setTextSize(9);
            stat.addView(textView);

            textView = new TextView(view.getContext());
            textView.setText("          ");
            stat.addView(textView);
            for (String quiz : quizes) {
                textView = new TextView(view.getContext());
                textView.setText(dbOperations.averageQuizResult(courseName,quiz));
                stat.addView(textView);
                textView.setTextSize(9);

            }
            tableLayout.addView(stat);
        }
        resultsByCourseName.close();
        return view;
    }


}
