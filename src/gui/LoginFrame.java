package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {

    JLabel lblTitle, lblUser, lblPass;
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin, btnExit;

    public LoginFrame() {

        setTitle("Faculty Workload Tracker");
        setSize(420,320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240,248,255));

        lblTitle = new JLabel("Faculty Workload Tracker");
        lblTitle.setFont(new Font("Arial", Font.BOLD,22));
        lblTitle.setBounds(60,20,320,30);

        lblUser = new JLabel("Username");
        lblUser.setBounds(50,80,100,25);

        txtUser = new JTextField();
        txtUser.setBounds(160,80,180,25);

        lblPass = new JLabel("Password");
        lblPass.setBounds(50,130,100,25);

        txtPass = new JPasswordField();
        txtPass.setBounds(160,130,180,25);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(70,200,100,35);

        btnExit = new JButton("Exit");
        btnExit.setBounds(220,200,100,35);

        btnLogin.addActionListener(this);
        btnExit.addActionListener(this);

        add(lblTitle);
        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);
        add(btnExit);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btnLogin){

            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());

            if(username.equals("admin") && password.equals("admin123")){

                JOptionPane.showMessageDialog(this,"Login Successful");

                dispose();

                new Dashboard();

            }else{

                JOptionPane.showMessageDialog(this,"Invalid Username or Password");

            }

        }

        if(e.getSource()==btnExit){

            System.exit(0);

        }

    }

}