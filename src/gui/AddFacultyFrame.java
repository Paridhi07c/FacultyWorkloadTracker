package gui;

import javax.swing.*;
import java.awt.*;

import model.Faculty;
import service.FacultyService;

public class AddFacultyFrame extends JFrame {

    JLabel lblTitle;
    JLabel lblId;
    JLabel lblName;
    JLabel lblDepartment;
    JLabel lblSubject;
    JLabel lblHours;

    JTextField txtId;
    JTextField txtName;
    JTextField txtDepartment;
    JTextField txtSubject;
    JTextField txtHours;

    JButton btnSave;
    JButton btnClear;
    JButton btnBack;

    FacultyService service = new FacultyService();

    public AddFacultyFrame() {

        setTitle("Add Faculty");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        lblTitle = new JLabel("Add Faculty");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(150, 20, 200, 30);

        lblId = new JLabel("Faculty ID");
        lblId.setBounds(40, 80, 100, 25);

        txtId = new JTextField();
        txtId.setBounds(160, 80, 200, 25);

        lblName = new JLabel("Faculty Name");
        lblName.setBounds(40, 120, 100, 25);

        txtName = new JTextField();
        txtName.setBounds(160, 120, 200, 25);

        lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(40, 160, 100, 25);

        txtDepartment = new JTextField();
        txtDepartment.setBounds(160, 160, 200, 25);

        lblSubject = new JLabel("Subject");
        lblSubject.setBounds(40, 200, 100, 25);

        txtSubject = new JTextField();
        txtSubject.setBounds(160, 200, 200, 25);

        lblHours = new JLabel("Hours / Week");
        lblHours.setBounds(40, 240, 100, 25);

        txtHours = new JTextField();
        txtHours.setBounds(160, 240, 200, 25);

        btnSave = new JButton("Save");
        btnSave.setBounds(40, 320, 90, 35);

        btnClear = new JButton("Clear");
        btnClear.setBounds(165, 320, 90, 35);

        btnBack = new JButton("Back");
        btnBack.setBounds(290, 320, 90, 35);

        add(lblTitle);
        add(lblId);
        add(txtId);
        add(lblName);
        add(txtName);
        add(lblDepartment);
        add(txtDepartment);
        add(lblSubject);
        add(txtSubject);
        add(lblHours);
        add(txtHours);

        add(btnSave);
        add(btnClear);
        add(btnBack);
                // Save Button
        btnSave.addActionListener(e -> {

            try {

                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                String department = txtDepartment.getText();
                String subject = txtSubject.getText();
                int hours = Integer.parseInt(txtHours.getText());

                if(name.isEmpty() || department.isEmpty() || subject.isEmpty()){

                    JOptionPane.showMessageDialog(this,
                            "Please fill all fields.");

                    return;
                }

                Faculty faculty = new Faculty(
                        id,
                        name,
                        department,
                        subject,
                        hours);

                service.addFaculty(faculty);

                JOptionPane.showMessageDialog(this,
                        "Faculty Added Successfully!");

                txtId.setText("");
                txtName.setText("");
                txtDepartment.setText("");
                txtSubject.setText("");
                txtHours.setText("");

            }
            catch(NumberFormatException ex){

                JOptionPane.showMessageDialog(this,
                        "Faculty ID and Hours must be numbers.");

            }

        });

        // Clear Button
        btnClear.addActionListener(e -> {

            txtId.setText("");
            txtName.setText("");
            txtDepartment.setText("");
            txtSubject.setText("");
            txtHours.setText("");

        });

        // Back Button
        btnBack.addActionListener(e -> {

            dispose();

        });

        setVisible(true);

    }

}