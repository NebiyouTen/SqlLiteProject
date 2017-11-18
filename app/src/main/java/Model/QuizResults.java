//On my honor, as a Carnegie-Mellon Rwanda student,
// I have neither given nor received unauthorized
// assistance on this work.

package Model;

/**
 * Created by nyismaw on 11/18/2017.
 */
public class QuizResults {
    private String quizResultsId;
    private String quizName;
    private String courseName;
    private String studentId;
    private String resut;
    public QuizResults(){

        resut="N.A.";
    }
    public String getQuizResultsId() {
        return quizResultsId;
    }

    public void setQuizResultsId(String quizResultsId) {
        this.quizResultsId = quizResultsId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getResut() {
        return resut;
    }

    public void setResut(String resut) {
        this.resut = resut;
    }
}
