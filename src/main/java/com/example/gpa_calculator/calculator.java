package com.example.gpa_calculator;

class Course {
    private String courseName;
    private String courseCode;
    private String teacherName;
    private double credit;
    private double grade;

    public Course(String courseName, String courseCode, String teacherName, double credit, double grade) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.teacherName = teacherName;
        this.credit = credit;
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public double getCredit() {
        return credit;
    }

    public double getGrade() {
        return grade;
    }
}
