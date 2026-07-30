package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import model.Faculty;
import service.FacultyService;

public class ViewFacultyFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    JButton btnRefresh;
    JButton btnBack;

    FacultyService service = new FacultyService();

    public ViewFacultyFrame() {

        setTitle("View Faculty");
        setSize(700,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Faculty Records", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD,22));

        model = new DefaultTableModel();

        model.addColumn("Faculty ID");
        model.addColumn("Faculty Name");
        model.addColumn("Department");
        model.addColumn("Subject");
        model.addColumn("Hours/Week");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();

        btnRefresh = new JButton("Refresh");
        btnBack = new JButton("Back");

        panel.add(btnRefresh);
        panel.add(btnBack);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
                loadFacultyData();

        btnRefresh.addActionListener(e -> {

            loadFacultyData();

        });

        btnBack.addActionListener(e -> {

            dispose();

        });

        setVisible(true);

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
                    faculty.getHoursPerWeek()

            });

        }

    }

}
