package gui;

import javax.swing.*;
import java.awt.*;

import service.FacultyService;

public class DeleteFacultyFrame extends JFrame {

    JLabel lblTitle;
    JLabel lblId;

    JTextField txtId;

    JButton btnDelete;
    JButton btnBack;

    FacultyService service = new FacultyService();

    public DeleteFacultyFrame() {

        setTitle("Delete Faculty");
        setSize(450,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        lblTitle = new JLabel("Delete Faculty");
        lblTitle.setFont(new Font("Arial", Font.BOLD,22));
        lblTitle.setBounds(130,20,250,30);

        lblId = new JLabel("Faculty ID");
        lblId.setBounds(40,90,100,25);

        txtId = new JTextField();
        txtId.setBounds(150,90,180,25);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(70,170,120,35);

        btnBack = new JButton("Back");
        btnBack.setBounds(230,170,120,35);

        add(lblTitle);
        add(lblId);
        add(txtId);
        add(btnDelete);
        add(btnBack);
                // Delete Button
        btnDelete.addActionListener(e -> {

            try {

                int id = Integer.parseInt(txtId.getText());

                boolean deleted = service.deleteFaculty(id);

                if (deleted) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Faculty Deleted Successfully!");

                    txtId.setText("");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Faculty not found.");

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