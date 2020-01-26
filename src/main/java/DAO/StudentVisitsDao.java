package DAO;

import Model.StudentVisits;
import Model.StudentVisitsJoin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentVisitsDao {
    private static final String SQL_INSERT =
            "INSERT INTO Student_visits(student_id, lesson_id)\n" +
                    "VALUES (?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE Student_visits\n" +
                    "SET student_id=?, lesson_id=?\n" +
                    "WHERE id=?;";

    private static final String SQL_DELETE =
            "DELETE FROM Student_visits\n" +
                    "WHERE id=?";

    private static final String SQL_GET_BY_PK =
            "SELECT id, student_id, lesson_id\n" +
                    "FROM Student_visits\n" +
                    "WHERE id=?;";

    private static final String SQL_GET_ALL =
            "SELECT id, student_id, lesson_id\n" +
                    "FROM Student_visits;\n";

    private static String SQL_GET_ALL_JOIN =
            "SELECT l.\"DATE\", l.TITLE, CONCAT(s.FIRST_NAME, ' ', s.middle_name, ' ', s.surname) AS NAME FROM STUDENT_VISITS sv\n" +
                    "JOIN STUDENTS s ON s.ID = sv.STUDENT_ID\n" +
                    "JOIN LESSONS l ON l.ID = sv.LESSON_ID;";

    private Connection connection;

    public StudentVisitsDao(Connection connection) {
        this.connection = connection;
    }

    public void insert(StudentVisits studentVisits) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, studentVisits.getStudentId());
            statement.setInt(2, studentVisits.getLessonId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Creating studentVisits failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    studentVisits.setId(generatedKeys.getInt(1));
                } else {
                    throw new RuntimeException("Creating studentVisits failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Creating studentVisits failed, sql exception: " + e.getMessage());
        }
    }

    public void update(StudentVisits studentVisits) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setInt(1, studentVisits.getStudentId());
            statement.setInt(2, studentVisits.getLessonId());
            statement.setInt(3, studentVisits.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Updating studentVisits failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Updating studentVisits failed, sql exception: " + e.getMessage());
        }
    }

    public void delete(StudentVisits studentVisits) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {

            statement.setInt(1, studentVisits.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Deleting studentVisits failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Deleting studentVisits failed, sql exception: " + e.getMessage());
        }
    }

    public StudentVisits getByPK(int primaryKey) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_PK)) {
            statement.setInt(1, primaryKey);

            try(ResultSet studentVisitFromDB = statement.executeQuery()){
                StudentVisits studentVisits = new StudentVisits();
                studentVisitFromDB.next();

                studentVisits.setId(studentVisitFromDB.getInt("id"));
                studentVisits.setStudentId(studentVisitFromDB.getInt("student_id"));
                studentVisits.setLessonId(studentVisitFromDB.getInt("lesson_id"));

                return studentVisits;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Getting studentVisit failed, sql exception: " + e.getMessage());
        }
    }

    public List<StudentVisits> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL)) {
            try(ResultSet studentsVisitFromDB = statement.executeQuery()){

                List<StudentVisits> studentVisitsList = new ArrayList<>();

                while (studentsVisitFromDB.next()) {
                    StudentVisits studentVisits = new StudentVisits();
                    studentVisits.setId(studentsVisitFromDB.getInt("id"));
                    studentVisits.setStudentId(studentsVisitFromDB.getInt("student_id"));
                    studentVisits.setLessonId(studentsVisitFromDB.getInt("lesson_id"));
                    studentVisitsList.add(studentVisits);
                }

                return studentVisitsList;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Getting studentVisit failed, sql exception: " + e.getMessage());
        }
    }

    public List<StudentVisitsJoin> getAllJoin() {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_JOIN)) {
            try(ResultSet studentsVisitFromDB = statement.executeQuery()){

                List<StudentVisitsJoin> studentVisitsList = new ArrayList<>();

                while (studentsVisitFromDB.next()) {
                    StudentVisitsJoin studentVisits = new StudentVisitsJoin();
                    studentVisits.setDate(studentsVisitFromDB.getTimestamp("date"));
                    studentVisits.setTitle(studentsVisitFromDB.getString("title"));
                    studentVisits.setName(studentsVisitFromDB.getString("name"));
                    studentVisitsList.add(studentVisits);
                }

                return studentVisitsList;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Getting studentVisit failed, sql exception: " + e.getMessage());
        }
    }
}
