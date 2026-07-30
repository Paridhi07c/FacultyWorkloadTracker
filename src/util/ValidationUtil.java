package util;

import java.util.ArrayList;
import java.util.List;

import config.AppConfig;
import model.Faculty;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static List<String> validateFaculty(Faculty faculty, boolean isNew, boolean idExists) {
        List<String> errors = new ArrayList<>();

        if (faculty.getFacultyId() <= 0) {
            errors.add("Faculty ID must be a positive number.");
        } else if (isNew && idExists) {
            errors.add("Faculty ID already exists. Use a unique ID.");
        }

        if (isBlank(faculty.getFacultyName())) {
            errors.add("Faculty name is required.");
        } else if (faculty.getFacultyName().trim().length() < 2) {
            errors.add("Faculty name must be at least 2 characters.");
        }

        if (isBlank(faculty.getDepartment())) {
            errors.add("Department is required.");
        }

        if (isBlank(faculty.getSubject())) {
            errors.add("Subject is required.");
        }

        if (faculty.getHoursPerWeek() < AppConfig.MIN_HOURS_PER_WEEK) {
            errors.add("Hours per week must be at least " + AppConfig.MIN_HOURS_PER_WEEK + ".");
        } else if (faculty.getHoursPerWeek() > AppConfig.MAX_ALLOWED_HOURS) {
            errors.add("Hours per week cannot exceed " + AppConfig.MAX_ALLOWED_HOURS + ".");
        }

        return errors;
    }

    public static List<String> validateFacultyId(String idText) {
        List<String> errors = new ArrayList<>();
        if (isBlank(idText)) {
            errors.add("Faculty ID is required.");
            return errors;
        }
        try {
            int id = Integer.parseInt(idText.trim());
            if (id <= 0) {
                errors.add("Faculty ID must be a positive number.");
            }
        } catch (NumberFormatException e) {
            errors.add("Faculty ID must be a valid number.");
        }
        return errors;
    }

    public static List<String> validateHours(String hoursText) {
        List<String> errors = new ArrayList<>();
        if (isBlank(hoursText)) {
            errors.add("Hours per week is required.");
            return errors;
        }
        try {
            int hours = Integer.parseInt(hoursText.trim());
            if (hours < AppConfig.MIN_HOURS_PER_WEEK) {
                errors.add("Hours per week must be at least " + AppConfig.MIN_HOURS_PER_WEEK + ".");
            } else if (hours > AppConfig.MAX_ALLOWED_HOURS) {
                errors.add("Hours per week cannot exceed " + AppConfig.MAX_ALLOWED_HOURS + ".");
            }
        } catch (NumberFormatException e) {
            errors.add("Hours per week must be a valid number.");
        }
        return errors;
    }

    public static String formatErrors(List<String> errors) {
        return String.join("\n", errors);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
