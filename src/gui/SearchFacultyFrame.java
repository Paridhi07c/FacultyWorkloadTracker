package gui;

import javax.swing.*;
import java.awt.*;

import model.Faculty;
import service.FacultyService;

public class SearchFacultyFrame extends JFrame {

    JLabel lblTitle;
    JLabel lblId;

    JTextField txtId;

    JTextArea resultArea;

    JButton btnSearch;
    JButton btnBack;

    FacultyService service = new FacultyService();

    public SearchFacultyFrame() {

        setTitle("Search Faculty");
        setSize(500,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        lblTitle = new JLabel("Search Faculty");
        lblTitle.setFont(new Font("Arial", Font.BOLD,22));
        lblTitle.setBounds(150,20,250,30);

        lblId = new JLabel("Faculty ID");

        lblId.setBounds(40,80,100,25);

        txtId = new JTextField();

        txtId.setBounds(140,80,150,25);

        btnSearch = new JButton("Search");

        btnSearch.setBounds(310,80,100,25);

        resultArea = new JTextArea();

        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        scrollPane.setBounds(40,140,390,180);

        btnBack = new JButton("Back");

        btnBack.setBounds(180,340,100,35);

        add(lblTitle);
        add(lblId);
        add(txtId);
        add(btnSearch);
        add(scrollPane);
        add(btnBack);
                // Search Button
        btnSearch.addActionListener(e -> {

            try {

                int id = Integer.parseInt(txtId.getText());

                Faculty faculty = service.searchFaculty(id);

                if (faculty != null) {

                    resultArea.setText(
                            "Faculty ID : " + faculty.getFacultyId()
                            + "\nFaculty Name : " + faculty.getFacultyName()
                            + "\nDepartment : " + faculty.getDepartment()
                            + "\nSubject : " + faculty.getSubject()
                            + "\nHours / Week : " + faculty.getHoursPerWeek());

                } else {

                    resultArea.setText("Faculty not found.");

                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid Faculty ID.");

            }

        });

        // Back Button
        btnBack.addActionListener(e -> {

            dispose();

        });

        setVisible(true);

    }

}
