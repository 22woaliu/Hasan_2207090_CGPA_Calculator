package com.example.gpa_calculator;

public class GpaRecord {
    private int id;
    private String studentName;
    private double gpa;
    private String timestamp;

    public GpaRecord(int id, String studentName, double gpa, String timestamp) {
        this.id = id;
        this.studentName = studentName;
        this.gpa = gpa;
        this.timestamp = timestamp;
    }

    public GpaRecord(String studentName, double gpa, String timestamp) {
        this.studentName = studentName;
        this.gpa = gpa;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
