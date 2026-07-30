package service;

import java.util.ArrayList;

import model.Faculty;
import util.FileManager;

public class FacultyService {

    private ArrayList<Faculty> facultyList;

    public FacultyService() {
        facultyList = FileManager.loadFaculty();
    }

    // Add Faculty
    public void addFaculty(Faculty faculty) {
        facultyList.add(faculty);
        FileManager.saveFaculty(facultyList);
    }

    // View All Faculty
    public ArrayList<Faculty> getAllFaculty() {
        return facultyList;
    }

    // Search Faculty
    public Faculty searchFaculty(int id) {

        for (Faculty faculty : facultyList) {

            if (faculty.getFacultyId() == id) {
                return faculty;
            }

        }

        return null;
    }

    // Update Faculty
    public boolean updateFaculty(int id, String name, String department,
                                 String subject, int hours) {

        Faculty faculty = searchFaculty(id);

        if (faculty != null) {

            faculty.setFacultyName(name);
            faculty.setDepartment(department);
            faculty.setSubject(subject);
            faculty.setHoursPerWeek(hours);

            FileManager.saveFaculty(facultyList);

            return true;
        }

        return false;
    }

    // Delete Faculty
    public boolean deleteFaculty(int id) {

        Faculty faculty = searchFaculty(id);

        if (faculty != null) {

            facultyList.remove(faculty);

            FileManager.saveFaculty(facultyList);

            return true;
        }

        return false;
    }

    // Calculate Total Workload
    public int calculateTotalWorkload() {

        int total = 0;

        for (Faculty faculty : facultyList) {

            total += faculty.getHoursPerWeek();

        }

        return total;
    }

}