package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import model.Faculty;

/**
 * Export faculty data and workload reports to files.
 */
public class ReportService {

    private static final String REPORT_DIR = "reports";
    private final FacultyService facultyService;
    private final WorkloadService workloadService;

    public ReportService(FacultyService facultyService, WorkloadService workloadService) {
        this.facultyService = facultyService;
        this.workloadService = workloadService;
    }

    public String exportFacultyCsv() throws IOException {
        ensureReportDir();
        String fileName = REPORT_DIR + "/faculty_export_"
                + timestamp() + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Faculty ID,Faculty Name,Department,Subject,Hours/Week,Workload Status");
            for (Faculty faculty : facultyService.getAllFaculty()) {
                writer.printf("%d,%s,%s,%s,%d,%s%n",
                        faculty.getFacultyId(),
                        escapeCsv(faculty.getFacultyName()),
                        escapeCsv(faculty.getDepartment()),
                        escapeCsv(faculty.getSubject()),
                        faculty.getHoursPerWeek(),
                        workloadService.getWorkloadStatus(faculty));
            }
        }
        return fileName;
    }

    public String exportWorkloadReport() throws IOException {
        ensureReportDir();
        String fileName = REPORT_DIR + "/workload_report_"
                + timestamp() + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.print(workloadService.buildSummaryReport());
            writer.println("\nGenerated: "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return fileName;
    }

    public String exportDepartmentReport() throws IOException {
        ensureReportDir();
        String fileName = REPORT_DIR + "/department_report_"
                + timestamp() + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Department,Total Hours,Faculty Count");
            Map<String, Integer> deptHours = workloadService.getWorkloadByDepartment();
            for (Map.Entry<String, Integer> entry : deptHours.entrySet()) {
                int count = countFacultyInDepartment(entry.getKey());
                writer.printf("%s,%d,%d%n",
                        escapeCsv(entry.getKey()), entry.getValue(), count);
            }
        }
        return fileName;
    }

    private int countFacultyInDepartment(String department) {
        int count = 0;
        for (Faculty faculty : facultyService.getAllFaculty()) {
            if (faculty.getDepartment().equalsIgnoreCase(department)) {
                count++;
            }
        }
        return count;
    }

    private void ensureReportDir() {
        File dir = new File(REPORT_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }

    private String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
