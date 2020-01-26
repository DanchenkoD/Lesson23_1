package Model;

public class Student {
    private int id;
    private String firstName;
    private String middle_name;
    private String surname;
    private float age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public Student(int id, String firstName, String middle_name, String surname, float age) {
        this.id = id;
        this.firstName = firstName;
        this.middle_name = middle_name;
        this.surname = surname;
        this.age = age;
    }
    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
