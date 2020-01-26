package Model;

public class StudentVisits {
    private int id;
    private int studentId;
    private int lessonId;

    public StudentVisits(int id, int studentId, int lessonId) {
        this.id = id;
        this.studentId = studentId;
        this.lessonId = lessonId;
    }

    public StudentVisits() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public String toString() {
        return "StudentVisits{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", lessonId=" + lessonId +
                '}';
    }
}
