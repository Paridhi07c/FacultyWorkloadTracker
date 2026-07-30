package model;

public class Faculty {

    private int facultyId;
    private String facultyName;
    private String department;
    private String subject;
    private int hoursPerWeek;

    // Default Constructor
    public Faculty() {
    }

    // Parameterized Constructor
    public Faculty(int facultyId, String facultyName,
                   String department, String subject,
                   int hoursPerWeek) {

        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.department = department;
        this.subject = subject;
        this.hoursPerWeek = hoursPerWeek;
    }

    // Getters
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

    // Setters
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

    @Override
    public String toString() {
        return "Faculty ID : " + facultyId +
                "\nFaculty Name : " + facultyName +
                "\nDepartment : " + department +
                "\nSubject : " + subject +
                "\nHours/Week : " + hoursPerWeek;
    }
}