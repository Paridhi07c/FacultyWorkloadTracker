package util;

import java.util.ArrayList;

import repository.FileFacultyRepository;

/**
 * @deprecated Use {@link repository.FileFacultyRepository} instead.
 */
@Deprecated
public class FileManager {

    private FileManager() {
    }

    @Deprecated
    public static void saveFaculty(ArrayList<model.Faculty> facultyList) {
        FileFacultyRepository repo = new FileFacultyRepository();
        repo.saveAll(facultyList);
    }

    @Deprecated
    public static ArrayList<model.Faculty> loadFaculty() {
        FileFacultyRepository repo = new FileFacultyRepository();
        return new ArrayList<>(repo.findAll());
    }
}
