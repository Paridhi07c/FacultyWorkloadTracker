package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Faculty;
import service.FacultyService;
import util.UITheme;

public class ViewFacultyFrame extends JFrame {

    private DefaultTableModel model;
    private final FacultyService service = FacultyService.getInstance();
    private final Dashboard dashboard;

    public ViewFacultyFrame() {
        this(null);
    }

    public ViewFacultyFrame(Dashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("View Faculty");
        setSize(820, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UITheme.applyFrameDefaults(this);
        buildUI();
        loadFacultyData();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(UITheme.createHeaderPanel("Faculty Records", "All registered faculty members"), BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"Faculty ID", "Faculty Name", "Department", "Subject", "Hours/Week", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        UITheme.styleTable(table);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UITheme.BACKGROUND);
        center.setBorder(new EmptyBorder(16, 20, 16, 20));
        center.add(UITheme.createTableScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = UITheme.createPrimaryButton("Refresh");
        btnRefresh.addActionListener(e -> loadFacultyData());

        JButton btnBack = UITheme.createSecondaryButton("Back");
        btnBack.addActionListener(e -> dispose());

        JPanel footer = new JPanel();
        footer.setBackground(UITheme.BACKGROUND);
        footer.setBorder(new EmptyBorder(0, 20, 16, 20));
        footer.add(btnRefresh);
        footer.add(btnBack);

        add(center, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    private void loadFacultyData() {
        model.setRowCount(0);
        ArrayList<Faculty> facultyList = service.getAllFaculty();

        for (Faculty faculty : facultyList) {
            model.addRow(new Object[]{
                    faculty.getFacultyId(),
                    faculty.getFacultyName(),
                    faculty.getDepartment(),
                    faculty.getSubject(),
                    faculty.getHoursPerWeek(),
                    faculty.getWorkloadStatus()
            });
        }

        if (dashboard != null) {
            dashboard.refreshStats();
        }
    }
}
