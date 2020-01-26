import DAO.LessonsDao;
import DAO.StudentVisitsDao;
import DAO.StudentsDao;
import DAO.TableUtils;
import Model.Lesson;
import Model.Student;
import Model.StudentVisits;
import Model.StudentVisitsJoin;

import java.sql.*;
import java.util.List;

public class Main {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Can't load driver for h2 " + e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, "sa", "")) {

            System.out.println("\nDrop tables if exist");
            TableUtils.dropTables(connection);

            System.out.println("\nCreate tables");
            TableUtils.createTables(connection);

            //Test StudentDao
            StudentsDao studentsDao = new StudentsDao(connection);

            Student studentIvanov = new Student(0, "Иван", "Иванович","Иванов",17);
            Student studentPetrov = new Student(0, "Петр", "Петрович","Петров", 18);
            Student studentArtemov = new Student(0, "Артем", "Артемович","Артемов",19);

            System.out.println("\nAdding students");
            studentsDao.insert(studentIvanov);
            studentsDao.insert(studentPetrov);
            studentsDao.insert(studentArtemov);

            System.out.println(studentIvanov);
            System.out.println(studentPetrov);
            System.out.println(studentArtemov);

            System.out.println("\nGetting student from DB by PK");
            System.out.println(studentsDao.getByID(studentIvanov.getId()));

            System.out.println("\nGetting all students from DB");
            studentsDao.getAll().forEach(System.out::println);

            System.out.println("\nUpdate student Linus");
            studentPetrov.setFirstName("Виктор");
            studentPetrov.setMiddle_name("Викторович");
            studentsDao.update(studentPetrov);
            System.out.println(studentsDao.getByID(studentPetrov.getId()));

            System.out.println("\nDelete student Bill");
            studentsDao.delete(studentIvanov);
            studentsDao.getAll().forEach(System.out::println);

            //Test LessonDao
            LessonsDao lessonsDao = new LessonsDao(connection);

            System.out.println("\nAdding lessons");
            Lesson firstLesson = new Lesson(0, "Математика", Timestamp.valueOf("2020-01-25 09:00:00"));
            Lesson secondLesson = new Lesson(0, "Физика", Timestamp.valueOf("2020-01-25 11:00:00"));
            Lesson thirdLesson = new Lesson(0, "Программирование", Timestamp.valueOf("2020-01-25 13:00:00"));

            lessonsDao.insert(firstLesson);
            lessonsDao.insert(secondLesson);
            lessonsDao.insert(thirdLesson);

            System.out.println("\nGetting lesson from DB by PK");
            System.out.println(lessonsDao.getByPK(firstLesson.getId()));

            System.out.println("\nGetting all lessons from DB");
            lessonsDao.getAll().forEach(System.out::println);

            System.out.println("\nUpdate second lesson");
            secondLesson.setTitle("PHYSICS");
            secondLesson.setDate(Timestamp.valueOf("2020-01-25 11:30:00"));
            lessonsDao.update(secondLesson);
            System.out.println(lessonsDao.getByPK(secondLesson.getId()));

            System.out.println("\nDelete third lesson");
            lessonsDao.delete(thirdLesson);
            lessonsDao.getAll().forEach(System.out::println);

            //Test StudentVisitsDao
            StudentVisitsDao studentVisitsDao = new StudentVisitsDao(connection);

            System.out.println("\nAdding student visits");
            StudentVisits visit1 = new StudentVisits(0, 2,1);
            StudentVisits visit2 = new StudentVisits(0, 3,2);
            StudentVisits visit3 = new StudentVisits(0, 3,1);
            StudentVisits visit4 = new StudentVisits(0, 2,2);

            studentVisitsDao.insert(visit1);
            studentVisitsDao.insert(visit2);
            studentVisitsDao.insert(visit3);
            studentVisitsDao.insert(visit4);

            System.out.println("\nGetting student visits from DB by PK");
            System.out.println(studentVisitsDao.getByPK(visit1.getId()));

            System.out.println("\nGetting all student visits from DB");
            studentVisitsDao.getAll().forEach(System.out::println);

            System.out.println("\nUpdate student visits 3");
            visit3.setStudentId(3);
            visit3.setLessonId(1);
            studentVisitsDao.update(visit3);
            System.out.println(studentVisitsDao.getByPK(visit3.getId()));

            System.out.println("\nDelete student visits 3");
            studentVisitsDao.delete(visit3);
            studentVisitsDao.getAll().forEach(System.out::println);

            System.out.println("\nПосещения студентов");
            studentVisitsDao.getAllJoin().forEach(System.out::println);






        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connectToAndQueryDatabase() throws SQLException {
        try (
                /*Connection connection = DriverManager.getConnection( "jdbc:h2:./testdb”, username, password) Statement stmt = connection.createStatement())*/
                Connection conn = DriverManager.
                        getConnection("jdbc:h2:~/test", "sa", "");
                Statement stmt = conn.createStatement())
        {
            stmt.execute("Alter table STUDENTS alter COLUMN id INT NOT NULL AUTO_INCREMENT PRIMARY KEY");
            ResultSet resultSet= stmt.executeQuery("select ID ,NAME ,SURNAME ,MIDDLE_NAME ,AGE  from STUDENTS;");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String surname = resultSet.getString("SURNAME");
                String middle_name = resultSet.getString("MIDDLE_NAME");
                float age = resultSet.getFloat("AGE");

                System.out.println(id+ name+middle_name+surname+age);
            }
        }
    }

}