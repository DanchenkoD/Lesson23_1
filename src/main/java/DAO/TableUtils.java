package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableUtils {

    private static final String SQL_DROP_TABLES =
            "DROP TABLE IF EXISTS Student_visits;\n" +
                    "DROP TABLE IF EXISTS Students;\n" +
                    "DROP TABLE IF EXISTS Lessons;";

    private static final String SQL_CREATE_TABLES =
            "CREATE TABLE IF NOT EXISTS Students(\n" +
                    "  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  first_name VARCHAR(50) NOT NULL,\n" +
                    "  middle_name VARCHAR(50) NOT NULL,\n" +
                    "  surname VARCHAR(50) NOT NULL,\n" +
                    "  age float NOT NULL\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Lessons(\n" +
                    "  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  title VARCHAR(100) NOT NULL,\n" +
                    "  date Timestamp \n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Student_visits(\n" +
                    "  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  student_id INT,\n" +
                    "  lesson_id INT,\n" +
                    "  CONSTRAINT FK_StudentsStudentsVisit FOREIGN KEY (student_id) REFERENCES Students(id),\n" +
                    "  CONSTRAINT FK_LessonsStudentsVisit FOREIGN KEY (lesson_id) REFERENCES Lessons(id)\n" +
                    ");";

    public static void createTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_CREATE_TABLES);
        }
    }

    public static void dropTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_DROP_TABLES);
        }
    }
}
