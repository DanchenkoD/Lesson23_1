package DAO;

import Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentsDao {
    private static final String SQL_INSERT =
            "INSERT INTO Students(first_name, middle_name, surname, age )\n" +
                    "VALUES (?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE Students\n" +
                    "SET first_name=?, middle_name=?, surname=?, age=?\n" +
                    "WHERE id=?;";

    private static final String SQL_DELETE =
            "DELETE FROM Students\n" +
                    "WHERE id=?";

    private static final String SQL_GET_BY_ID =
            "SELECT id, first_name, middle_name, surname, age\n" +
                    "FROM Students\n" +
                    "WHERE id=?;";

    private static final String SQL_GET_ALL =
            "SELECT id, first_name, middle_name, surname, age\n" +
                    "FROM Students;\n";

    private Connection connection;

    public StudentsDao(Connection connection) {
        this.connection = connection;
    }

    public void insert(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getMiddle_name());
            statement.setString(3, student.getSurname());
            statement.setFloat(4, student.getAge());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Creating student failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getInt(1));
                } else {
                    throw new RuntimeException("Creating student failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Creating student failed, sql exception: " + e.getMessage());
        }
    }

    public void update(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getMiddle_name());
            statement.setString(3, student.getSurname());
            statement.setFloat(4, student.getAge());
            statement.setInt(5, student.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Updating student failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Updating student failed, sql exception: " + e.getMessage());
        }
    }

    public void delete(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {

            statement.setInt(1, student.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Deleting student failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Deleting student failed, sql exception: " + e.getMessage());
        }
    }

    public Student getByID(int primaryKey) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_ID)) {
            statement.setInt(1, primaryKey);

            try(ResultSet studentFromDB = statement.executeQuery()){
                Student student = new Student();
                studentFromDB.next();

                student.setId(studentFromDB.getInt("id"));
                student.setFirstName(studentFromDB.getString("first_name"));
                student.setMiddle_name(studentFromDB.getString("middle_name"));
                student.setSurname(studentFromDB.getString("surname"));
                student.setAge(studentFromDB.getFloat("age"));

                return student;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Getting student failed, sql exception: " + e.getMessage());
        }
    }

    public List<Student> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL)) {
            try(ResultSet studentsFromDB = statement.executeQuery()){

                List<Student> studentList = new ArrayList<>();

                while (studentsFromDB.next()) {
                    Student student = new Student();
                    student.setId(studentsFromDB.getInt("id"));
                    student.setFirstName(studentsFromDB.getString("first_name"));
                    student.setMiddle_name(studentsFromDB.getString("middle_name"));
                    student.setSurname(studentsFromDB.getString("surname"));
                    student.setAge(studentsFromDB.getFloat("age"));
                    studentList.add(student);
                }

                return studentList;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Getting student failed, sql exception: " + e.getMessage());
        }
    }

}
