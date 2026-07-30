package service;

import java.util.ArrayList;
import java.util.List;

import config.AppConfig;
import model.Faculty;
import repository.FacultyRepository;
import util.ValidationUtil;

public class FacultyService {

    private static FacultyService instance;

    private final FacultyRepository repository;

    private FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public static synchronized FacultyService getInstance() {
        if (instance == null) {
            instance = new FacultyService(AppConfig.getRepository());
        }
        return instance;
    }

    public List<String> validateAndAdd(Faculty faculty) {
        List<String> errors = ValidationUtil.validateFaculty(
                faculty, true, repository.existsById(faculty.getFacultyId()));
        if (!errors.isEmpty()) {
            return errors;
        }
        repository.save(faculty);
        return errors;
    }

    /** @deprecated Use validateAndAdd for validation. Kept for backward compatibility. */
    public void addFaculty(Faculty faculty) {
        repository.save(faculty);
    }

    public ArrayList<Faculty> getAllFaculty() {
        return new ArrayList<>(repository.findAll());
    }

    public Faculty searchFaculty(int id) {
        return repository.findById(id);
    }

    public List<String> validateAndUpdate(int id, String name, String department,
                                          String subject, int hours) {
        Faculty existing = repository.findById(id);
        if (existing == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Faculty not found.");
            return errors;
        }

        Faculty updated = new Faculty(id, name.trim(), department.trim(), subject.trim(), hours);
        List<String> errors = ValidationUtil.validateFaculty(updated, false, false);
        if (!errors.isEmpty()) {
            return errors;
        }

        repository.update(updated);
        return errors;
    }

    public boolean updateFaculty(int id, String name, String department,
                                 String subject, int hours) {
        List<String> errors = validateAndUpdate(id, name, department, subject, hours);
        return errors.isEmpty();
    }

    public boolean deleteFaculty(int id) {
        if (repository.findById(id) == null) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    public int calculateTotalWorkload() {
        int total = 0;
        for (Faculty faculty : repository.findAll()) {
            total += faculty.getHoursPerWeek();
        }
        return total;
    }

    public WorkloadService getWorkloadService() {
        return new WorkloadService(this);
    }

    public ReportService getReportService() {
        return new ReportService(this, getWorkloadService());
    }

    public void refreshFromStorage() {
        repository.reload();
    }
}
