package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Faculty;

/**
 * File-based persistence (CSV). Compatible with existing data/faculty.txt format.
 */
public class FileFacultyRepository implements FacultyRepository {

    private static final String FILE_NAME = "data/faculty.txt";
    private List<Faculty> cache;

    public FileFacultyRepository() {
        cache = loadFromFile();
    }

    @Override
    public synchronized List<Faculty> findAll() {
        return new ArrayList<>(cache);
    }

    @Override
    public synchronized Faculty findById(int facultyId) {
        for (Faculty faculty : cache) {
            if (faculty.getFacultyId() == facultyId) {
                return faculty;
            }
        }
        return null;
    }

    @Override
    public synchronized void save(Faculty faculty) {
        cache.add(faculty);
        persist();
    }

    @Override
    public synchronized void saveAll(List<Faculty> facultyList) {
        cache = new ArrayList<>(facultyList);
        persist();
    }

    @Override
    public synchronized void update(Faculty faculty) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getFacultyId() == faculty.getFacultyId()) {
                cache.set(i, faculty);
                persist();
                return;
            }
        }
    }

    @Override
    public synchronized void deleteById(int facultyId) {
        cache.removeIf(f -> f.getFacultyId() == facultyId);
        persist();
    }

    @Override
    public synchronized boolean existsById(int facultyId) {
        return findById(facultyId) != null;
    }

    @Override
    public synchronized void reload() {
        cache = loadFromFile();
    }

    private void persist() {
        try {
            File folder = new File("data");
            if (!folder.exists()) {
                folder.mkdir();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
                for (Faculty faculty : cache) {
                    writer.println(
                            faculty.getFacultyId() + "," +
                            escape(faculty.getFacultyName()) + "," +
                            escape(faculty.getDepartment()) + "," +
                            escape(faculty.getSubject()) + "," +
                            faculty.getHoursPerWeek());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving faculty data.", e);
        }
    }

    private List<Faculty> loadFromFile() {
        List<Faculty> facultyList = new ArrayList<>();

        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return facultyList;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] data = line.split(",", -1);
                    if (data.length < 5) {
                        continue;
                    }
                    facultyList.add(new Faculty(
                            Integer.parseInt(data[0].trim()),
                            unescape(data[1].trim()),
                            unescape(data[2].trim()),
                            unescape(data[3].trim()),
                            Integer.parseInt(data[4].trim())));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading faculty data.", e);
        }

        return facultyList;
    }

    private static String escape(String value) {
        return value.replace(",", ";");
    }

    private static String unescape(String value) {
        return value.replace(";", ",");
    }
}
