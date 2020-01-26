package Model;

import java.sql.Timestamp;

public class Lesson {
    private int id;
    private String title;
    private Timestamp date;

    public Lesson(int id, String title, Timestamp date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Lesson() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
