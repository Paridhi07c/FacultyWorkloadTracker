package gui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    JButton btnAdd, btnView, btnSearch, btnUpdate, btnDelete, btnWorkload, btnExit;

    public Dashboard() {

        setTitle("Faculty Workload Tracker - Dashboard");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel heading = new JLabel("Faculty Workload Tracker Dashboard");
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setBounds(130, 20, 450, 30);

        btnAdd = new JButton("Add Faculty");
        btnAdd.setBounds(250, 80, 180, 40);

        btnView = new JButton("View Faculty");
        btnView.setBounds(250, 140, 180, 40);

        btnSearch = new JButton("Search Faculty");
        btnSearch.setBounds(250, 200, 180, 40);

        btnUpdate = new JButton("Update Faculty");
        btnUpdate.setBounds(250, 260, 180, 40);

        btnDelete = new JButton("Delete Faculty");
        btnDelete.setBounds(250, 320, 180, 40);

        btnWorkload = new JButton("Calculate Workload");
        btnWorkload.setBounds(250, 380, 180, 40);

        btnExit = new JButton("Exit");
        btnExit.setBounds(250, 440, 180, 40);

        add(heading);
        add(btnAdd);
        add(btnView);
        add(btnSearch);
        add(btnUpdate);
        add(btnDelete);
        add(btnWorkload);
        add(btnExit);

        btnAdd.addActionListener(e -> new AddFacultyFrame());

        btnView.addActionListener(e -> new ViewFacultyFrame());

        btnSearch.addActionListener(e -> new SearchFacultyFrame());

        btnUpdate.addActionListener(e -> new UpdateFacultyFrame());

        btnDelete.addActionListener(e -> new DeleteFacultyFrame());

        btnWorkload.addActionListener(e -> {

            service.FacultyService service = new service.FacultyService();

            JOptionPane.showMessageDialog(
                    this,
                    "Total Weekly Workload = "
                    + service.calculateTotalWorkload()
                    + " Hours");

        });

        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

}