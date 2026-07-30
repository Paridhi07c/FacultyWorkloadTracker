package service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import config.AppConfig;
import model.Faculty;

/**
 * Advanced workload analytics beyond simple totals.
 */
public class WorkloadService {

    private final FacultyService facultyService;

    public WorkloadService(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    public int calculateTotalWorkload() {
        int total = 0;
        for (Faculty faculty : facultyService.getAllFaculty()) {
            total += faculty.getHoursPerWeek();
        }
        return total;
    }

    public double calculateAverageWorkload() {
        List<Faculty> list = facultyService.getAllFaculty();
        if (list.isEmpty()) {
            return 0;
        }
        return (double) calculateTotalWorkload() / list.size();
    }

    public Map<String, Integer> getWorkloadByDepartment() {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Faculty faculty : facultyService.getAllFaculty()) {
            String dept = faculty.getDepartment();
            map.merge(dept, faculty.getHoursPerWeek(), Integer::sum);
        }
        return map;
    }

    public List<Faculty> getOverloadedFaculty() {
        List<Faculty> overloaded = new ArrayList<>();
        for (Faculty faculty : facultyService.getAllFaculty()) {
            if (faculty.getHoursPerWeek() > AppConfig.MAX_HOURS_PER_WEEK) {
                overloaded.add(faculty);
            }
        }
        return overloaded;
    }

    public List<Faculty> getUnderloadedFaculty() {
        List<Faculty> underloaded = new ArrayList<>();
        for (Faculty faculty : facultyService.getAllFaculty()) {
            if (faculty.getHoursPerWeek() < AppConfig.MAX_HOURS_PER_WEEK) {
                underloaded.add(faculty);
            }
        }
        return underloaded;
    }

    public int getFacultyCount() {
        return facultyService.getAllFaculty().size();
    }

    public int getDepartmentCount() {
        return getWorkloadByDepartment().size();
    }

    public String getWorkloadStatus(Faculty faculty) {
        if (faculty.getHoursPerWeek() > AppConfig.MAX_HOURS_PER_WEEK) {
            return "Overloaded";
        }
        if (faculty.getHoursPerWeek() == AppConfig.MAX_HOURS_PER_WEEK) {
            return "Full Load";
        }
        return "Underloaded";
    }

    public String buildSummaryReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Faculty Workload Summary ===\n\n");
        sb.append("Total Faculty        : ").append(getFacultyCount()).append("\n");
        sb.append("Total Weekly Hours   : ").append(calculateTotalWorkload()).append("\n");
        sb.append("Average Hours/Faculty: ")
                .append(String.format("%.1f", calculateAverageWorkload())).append("\n");
        sb.append("Standard Load        : ").append(AppConfig.MAX_HOURS_PER_WEEK).append(" hrs/week\n\n");

        sb.append("--- By Department ---\n");
        for (Map.Entry<String, Integer> entry : getWorkloadByDepartment().entrySet()) {
            sb.append(String.format("  %-30s %d hrs\n", entry.getKey(), entry.getValue()));
        }

        sb.append("\n--- Overloaded Faculty (> ")
                .append(AppConfig.MAX_HOURS_PER_WEEK).append(" hrs) ---\n");
        List<Faculty> overloaded = getOverloadedFaculty();
        if (overloaded.isEmpty()) {
            sb.append("  None\n");
        } else {
            for (Faculty f : overloaded) {
                sb.append(String.format("  [%d] %s - %d hrs\n",
                        f.getFacultyId(), f.getFacultyName(), f.getHoursPerWeek()));
            }
        }

        sb.append("\n--- Underloaded Faculty (< ")
                .append(AppConfig.MAX_HOURS_PER_WEEK).append(" hrs) ---\n");
        List<Faculty> underloaded = getUnderloadedFaculty();
        if (underloaded.isEmpty()) {
            sb.append("  None\n");
        } else {
            for (Faculty f : underloaded) {
                sb.append(String.format("  [%d] %s - %d hrs\n",
                        f.getFacultyId(), f.getFacultyName(), f.getHoursPerWeek()));
            }
        }

        return sb.toString();
    }
}
