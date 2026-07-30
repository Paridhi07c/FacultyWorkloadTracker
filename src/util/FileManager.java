package util;

import java.io.*;
import java.util.ArrayList;

import model.Faculty;

public class FileManager {

    private static final String FILE_NAME = "data/faculty.txt";

    // Save Faculty Data
    public static void saveFaculty(ArrayList<Faculty> facultyList) {

        try {

            File folder = new File("data");

            if (!folder.exists()) {
                folder.mkdir();
            }

            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));

            for (Faculty faculty : facultyList) {

                writer.println(
                        faculty.getFacultyId() + "," +
                        faculty.getFacultyName() + "," +
                        faculty.getDepartment() + "," +
                        faculty.getSubject() + "," +
                        faculty.getHoursPerWeek());

            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving faculty data.");

        }

    }

    // Load Faculty Data
    public static ArrayList<Faculty> loadFaculty() {

        ArrayList<Faculty> facultyList = new ArrayList<>();

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return facultyList;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                Faculty faculty = new Faculty(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        Integer.parseInt(data[4]));

                facultyList.add(faculty);

            }

            reader.close();

        } catch (Exception e) {

            System.out.println("Error loading faculty data.");

        }

        return facultyList;

    }

}