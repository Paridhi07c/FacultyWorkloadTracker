package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.AppConfig;
import config.DatabaseConfig;
import util.UITheme;

public class LoginFrame extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Faculty Workload Tracker - Login");
        setSize(480, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyFrameDefaults(this);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        add(UITheme.createHeaderPanel(
                "Faculty Workload Tracker",
                "Sign in to manage faculty workloads"), BorderLayout.NORTH);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(32, 40, 32, 40));

        GridBagConstraints gbc = UITheme.formConstraints(0);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = UITheme.createFormLabel("Username");
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        card.add(lblUser, gbc);

        txtUser = UITheme.createTextField();
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(txtUser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblPass = UITheme.createFormLabel("Password");
        card.add(lblPass, gbc);

        txtPass = new JPasswordField();
        txtPass.setFont(UITheme.FONT_BODY);
        txtPass.setPreferredSize(txtUser.getPreferredSize());
        gbc.gridx = 1;
        gbc.weightx = 1;
        card.add(txtPass, gbc);

        String storageHint = "file".equalsIgnoreCase(
                DatabaseConfig.getProperty("storage.type", "file"))
                ? "Storage: File (data/faculty.txt)"
                : "Storage: MySQL";
        JLabel lblHint = new JLabel(storageHint);
        lblHint.setFont(UITheme.FONT_SMALL);
        lblHint.setForeground(UITheme.TEXT_SECONDARY);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new java.awt.Insets(16, 8, 8, 8);
        card.add(lblHint, gbc);

        JButton btnLogin = UITheme.createPrimaryButton("Login");
        btnLogin.addActionListener(e -> handleLogin());

        JButton btnExit = UITheme.createSecondaryButton("Exit");
        btnExit.addActionListener(e -> System.exit(0));

        gbc.gridy = 3;
        gbc.insets = new java.awt.Insets(12, 8, 0, 8);
        card.add(UITheme.createButtonBar(btnLogin, btnExit), gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BACKGROUND);
        wrapper.setBorder(new EmptyBorder(24, 40, 40, 40));
        wrapper.add(card, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);

        getRootPane().setDefaultButton(btnLogin);
    }

    private void handleLogin() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());

        if (username.equals(AppConfig.DEFAULT_ADMIN_USER)
                && password.equals(AppConfig.DEFAULT_ADMIN_PASS)) {
            dispose();
            new Dashboard();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
