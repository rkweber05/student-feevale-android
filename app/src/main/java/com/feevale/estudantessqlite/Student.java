package com.feevale.estudantessqlite;


import androidx.annotation.NonNull;

public class Student {

    private Long id;
    private String name;
    private String email;
    private String course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student{" +
                "id= " + id +
                ", name=' " + name + '\'' +
                ", email=' " + email + '\'' +
                ", course=' " + course + '\'' +
                '}';
    }
}
