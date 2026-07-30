package model;

import config.AppConfig;

public class Faculty {

    private int facultyId;
    private String facultyName;
    private String department;
    private String subject;
    private int hoursPerWeek;

    public Faculty() {
    }

    public Faculty(int facultyId, String facultyName,
                   String department, String subject,
                   int hoursPerWeek) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.department = department;
        this.subject = subject;
        this.hoursPerWeek = hoursPerWeek;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getDepartment() {
        return department;
    }

    public String getSubject() {
        return subject;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public String getWorkloadStatus() {
        if (hoursPerWeek > AppConfig.MAX_HOURS_PER_WEEK) {
            return "Overloaded";
        }
        if (hoursPerWeek == AppConfig.MAX_HOURS_PER_WEEK) {
            return "Full Load";
        }
        return "Underloaded";
    }

    @Override
    public String toString() {
        return "Faculty ID     : " + facultyId +
                "\nFaculty Name   : " + facultyName +
                "\nDepartment     : " + department +
                "\nSubject        : " + subject +
                "\nHours/Week     : " + hoursPerWeek +
                "\nWorkload Status: " + getWorkloadStatus();
    }
}
