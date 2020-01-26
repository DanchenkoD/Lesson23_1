package DAO;

import Model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonsDao {
    private static final String SQL_INSERT =
            "INSERT INTO Lessons(title, date)\n" +
                    "VALUES (?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE Lessons\n" +
                    "SET title=?, date=? \n" +
                    "WHERE id=?;";

    private static final String SQL_DELETE =
            "DELETE FROM Lessons \n" +
                    "WHERE id=?";

    private static final String SQL_GET_BY_PK =
            "SELECT id, title, date\n" +
                    "FROM Lessons\n" +
                    "WHERE id=?;";

    private static final String SQL_GET_ALL =
            "SELECT id, title, date\n" +
                    "FROM Lessons;\n";

    private Connection connection;

    public LessonsDao(Connection connection) {
        this.connection = connection;
    }

    public void insert(Lesson lesson) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, lesson.getTitle());
            statement.setTimestamp(2, lesson.getDate());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Creating lesson failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lesson.setId(generatedKeys.getInt(1));
                } else {
                    throw new RuntimeException("Creating lesson failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Creating lesson failed, sql exception: " + e.getMessage());
        }
    }

    public void update(Lesson lesson) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setString(1, lesson.getTitle());
            statement.setTimestamp(2, lesson.getDate());
            statement.setInt(3, lesson.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Updating lesson failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Updating lesson failed, sql exception: " + e.getMessage());
        }
    }

    public void delete(Lesson lesson) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {

            statement.setInt(1, lesson.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Deleting lesson failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Deleting lesson failed, sql exception: " + e.getMessage());
        }
    }

    public Lesson getByPK(int primaryKey) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_PK)) {
            statement.setInt(1, primaryKey);

            try(ResultSet lessonFromDB = statement.executeQuery()){
                Lesson lesson = new Lesson();
                lessonFromDB.next();

                lesson.setId(lessonFromDB.getInt("id"));
                lesson.setTitle(lessonFromDB.getString("title"));
                lesson.setDate(lessonFromDB.getTimestamp("date"));

                return lesson;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Getting lesson failed, sql exception: " + e.getMessage());
        }
    }

    public List<Lesson> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL)) {
            try(ResultSet lessonsFromDB = statement.executeQuery()){

                List<Lesson> lessonList = new ArrayList<>();

                while (lessonsFromDB.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(lessonsFromDB.getInt("id"));
                    lesson.setTitle(lessonsFromDB.getString("title"));
                    lesson.setDate(lessonsFromDB.getTimestamp("date"));
                    lessonList.add(lesson);
                }

                return lessonList;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Getting lesson failed, sql exception: " + e.getMessage());
        }
    }
}
