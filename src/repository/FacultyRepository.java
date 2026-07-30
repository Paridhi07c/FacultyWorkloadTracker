package repository;

import java.util.List;
import model.Faculty;

/**
 * Persistence abstraction for faculty records.
 * Implementations: file (default) and MySQL.
 */
public interface FacultyRepository {

    List<Faculty> findAll();

    Faculty findById(int facultyId);

    void save(Faculty faculty);

    void saveAll(List<Faculty> facultyList);

    void update(Faculty faculty);

    void deleteById(int facultyId);

    boolean existsById(int facultyId);

    /** Reload data from the underlying storage (no-op for live DB connections). */
    void reload();
}
