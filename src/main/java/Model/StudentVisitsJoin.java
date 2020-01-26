package Model;

import java.sql.Timestamp;

public class StudentVisitsJoin {
    private Timestamp date;
    private String title;
    private String name;

    public StudentVisitsJoin(Timestamp date, String title, String name) {
        this.date = date;
        this.title = title;
        this.name = name;
    }

    public StudentVisitsJoin() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentVisitsJoin{" +
                "date=" + date +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
