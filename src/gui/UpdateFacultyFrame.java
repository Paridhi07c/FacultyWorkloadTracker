package gui;

import javax.swing.*;
import java.awt.*;

import model.Faculty;
import service.FacultyService;

public class UpdateFacultyFrame extends JFrame {

    JLabel lblTitle, lblId, lblName, lblDepartment, lblSubject, lblHours;

    JTextField txtId, txtName, txtDepartment, txtSubject, txtHours;

    JButton btnSearch, btnUpdate, btnBack;

    FacultyService service = new FacultyService();

    public UpdateFacultyFrame() {

        setTitle("Update Faculty");
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        lblTitle = new JLabel("Update Faculty");
        lblTitle.setFont(new Font("Arial", Font.BOLD,22));
        lblTitle.setBounds(150,20,250,30);

        lblId = new JLabel("Faculty ID");
        lblId.setBounds(40,80,100,25);

        txtId = new JTextField();
        txtId.setBounds(150,80,120,25);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(300,80,100,25);

        lblName = new JLabel("Faculty Name");
        lblName.setBounds(40,140,100,25);

        txtName = new JTextField();
        txtName.setBounds(150,140,220,25);

        lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(40,180,100,25);

        txtDepartment = new JTextField();
        txtDepartment.setBounds(150,180,220,25);

        lblSubject = new JLabel("Subject");
        lblSubject.setBounds(40,220,100,25);

        txtSubject = new JTextField();
        txtSubject.setBounds(150,220,220,25);

        lblHours = new JLabel("Hours/Week");
        lblHours.setBounds(40,260,100,25);

        txtHours = new JTextField();
        txtHours.setBounds(150,260,220,25);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(90,340,120,35);

        btnBack = new JButton("Back");
        btnBack.setBounds(260,340,120,35);

        add(lblTitle);
        add(lblId);
        add(txtId);
        add(btnSearch);
        add(lblName);
        add(txtName);
        add(lblDepartment);
        add(txtDepartment);
        add(lblSubject);
        add(txtSubject);
        add(lblHours);
        add(txtHours);
        add(btnUpdate);
        add(btnBack);
                // Search Button
        btnSearch.addActionListener(e -> {

            try {

                int id = Integer.parseInt(txtId.getText());

                Faculty faculty = service.searchFaculty(id);

                if (faculty != null) {

                    txtName.setText(faculty.getFacultyName());
                    txtDepartment.setText(faculty.getDepartment());
                    txtSubject.setText(faculty.getSubject());
                    txtHours.setText(String.valueOf(faculty.getHoursPerWeek()));

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Faculty not found.");

                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Enter a valid Faculty ID.");

            }

        });

        // Update Button
        btnUpdate.addActionListener(e -> {

            try {

                int id = Integer.parseInt(txtId.getText());

                String name = txtName.getText();
                String department = txtDepartment.getText();
                String subject = txtSubject.getText();
                int hours = Integer.parseInt(txtHours.getText());

                boolean updated = service.updateFaculty(
                        id,
                        name,
                        department,
                        subject,
                        hours);

                if (updated) {

                    JOptionPane.showMessageDialog(this,
                            "Faculty Updated Successfully!");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Faculty not found.");

                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Hours and Faculty ID must be numbers.");

            }

        });

        // Back Button
        btnBack.addActionListener(e -> dispose());

        setVisible(true);

    }

}