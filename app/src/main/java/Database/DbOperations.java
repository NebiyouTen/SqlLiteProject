//On my honor, as a Carnegie-Mellon Rwanda student,
// I have neither given nor received unauthorized
// assistance on this work.

package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.Courses;
import Model.QuizResults;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.OPEN_READWRITE;

/**
 * Created by nyismaw on 11/10/2017.
 */

public class DbOperations {

    Context context;
    SQLiteDatabase mydatabase;

    public DbOperations(Context context) {
        mydatabase = context.openOrCreateDatabase("StudentManagment", OPEN_READWRITE, null);
        this.context = context;
    }

    public DbOperations() {
        mydatabase = context.openOrCreateDatabase("StudentManagment", OPEN_READWRITE, null);
    }

    public List<Courses> getCourses() {
        List<Courses> courses = new ArrayList();
        try {


            Cursor res = mydatabase.rawQuery("select * from Courses ;", null);
            Log.e("Tag", "columns are: " + res.getColumnCount());

            if (res.moveToFirst()) {
                do {
                    Courses courses1= new Courses();
                    courses1.setCourseName(res.getString(1));
                    courses.add(courses1);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());

        }
        return courses;
    }

    public Cursor getResultsByCourseName(String coursename) {
        try {
            Cursor res = mydatabase.rawQuery("select * from Students, Courses, QuizResults " +
                    "where Students.studentId=QuizResults.studentId and Courses.courseId= QuizResults.courseID and " +
                    " Courses.Name = '" + coursename + "';", null);
            res.close();
            return res;

        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());
            return null;

        }


    }

    public List<QuizResults> getCourseQuizes(String coursename) {
        try {
            Cursor res = mydatabase.rawQuery("select distinct quizName from Courses, QuizResults " +
                    "where Courses.courseId= QuizResults.courseID and " +
                    " Courses.Name = '" + coursename + "' ORDER BY quizResultsId DESC;", null);
            List<QuizResults> quizes = new ArrayList();
            if (res.moveToFirst()) {
                do {
                    Log.e("Quiz names", "Quiz names are " + res.getString(0));
                    QuizResults quizResults= new QuizResults();
                    quizResults.setQuizName(res.getString(0));
                    quizes.add(quizResults);
                } while (res.moveToNext());
            }
            res.close();
            return quizes;
        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());
            return null;

        }


    }

    public Courses getCourseId(String courseName) {
        Courses courses= new Courses();
        try {
            Cursor res = mydatabase.rawQuery("select courseId from Courses where " +
                    " Name = '" + courseName + "';", null);
            String courseId = "";

            if (res.moveToFirst()) {
                do {
                    courses.setCourseId(res.getString(0));
                    courseId = (res.getString(0));
                } while (res.moveToNext());
            }
            res.close();
            return courses;

        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());
            return null;

        }


    }

    public QuizResults getQuizResult(String studentId, String courseName, String quizName) {
        try {
            Cursor res = mydatabase.rawQuery("select result from QuizResults " +
                    "where studentId = '" + studentId + "' and " +
                    " quizName = '" + (quizName) + "' and " +
                    " courseId = '" + getCourseId(courseName).getCourseId() + "';", null);
            QuizResults quizes = new QuizResults();
            if (res.moveToFirst()) {
                do {

                    quizes.setResut(res.getString(0));
                } while (res.moveToNext());
            }
            res.close();
            return quizes;
        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());
            return null;

        }
    }

    public String maxQuizResult(String courseName, String quizName) {
        try {
            Cursor res = mydatabase.rawQuery("select max (result) from QuizResults " +
                    "where " +
                    " quizName = '" + (quizName) + "' and " +
                    " courseId = '" + getCourseId(courseName).getCourseId() + "';", null);
            String quizes = "";
            if (res.moveToFirst()) {
                do {

                    quizes = (res.getString(0));
                } while (res.moveToNext());
            }
            res.close();
            return quizes;
        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());
            return null;

        }
    }

    public String minQuizResult(String courseName, String quizName) {
        try {
            Cursor res = mydatabase.rawQuery("select min (result) from QuizResults " +
                    "where " +
                    " quizName = '" + (quizName) + "' and " +
                    " courseId = '" + getCourseId(courseName).getCourseId() + "';", null);
            String quizes = "";
            if (res.moveToFirst()) {
                do {

                    quizes = (res.getString(0));
                } while (res.moveToNext());
            }
            res.close();
            return quizes;
        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());
            return null;

        }
    }

    public String averageQuizResult(String courseName, String quizName) {

        String quizes = "";
        try {
            Cursor res = mydatabase.rawQuery("select avg(result) from QuizResults " +
                    "where " +
                    " quizName = '" + (quizName) + "' and " +
                    " courseId = '" + getCourseId(courseName).getCourseId() + "';", null);

            if (res.moveToFirst()) {
                do {

                    quizes = (res.getString(0));
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());

        }
        return quizes;
    }

    public Cursor getStudentsByID(String studentId) {
        try {
            Cursor res = mydatabase.rawQuery("select * from Students where studentId= " + studentId + ";", null);
            return res;
        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());
            return null;

        }
    }

    public Cursor getStudentsByCourse(String courseName) {
        try {
            Cursor res = mydatabase.rawQuery("select distinct" +
                    " studentId from QuizResults where courseId= " + getCourseId(courseName).getCourseId() + " ;", null);
            return res;
        } catch (SQLException e) {
            Log.e("SQLException ", e.getMessage());
            return null;

        }
    }

}
