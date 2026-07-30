package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Faculty;
import service.FacultyService;
import util.UITheme;
import util.ValidationUtil;

public class AddFacultyFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtDepartment;
    private JTextField txtSubject;
    private JTextField txtHours;

    private final FacultyService service = FacultyService.getInstance();
    private final Dashboard dashboard;

    public AddFacultyFrame() {
        this(null);
    }

    public AddFacultyFrame(Dashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Add Faculty");
        setSize(520, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UITheme.applyFrameDefaults(this);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(UITheme.createHeaderPanel("Add Faculty", "Register a new faculty member"), BorderLayout.NORTH);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(24, 32, 24, 32));

        GridBagConstraints gbc = UITheme.formConstraints(0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = addField(card, gbc, 0, "Faculty ID *");
        txtName = addField(card, gbc, 1, "Faculty Name *");
        txtDepartment = addField(card, gbc, 2, "Department *");
        txtSubject = addField(card, gbc, 3, "Subject *");
        txtHours = addField(card, gbc, 4, "Hours / Week *");

        JLabel hint = new JLabel("Standard full load: 40 hrs/week  |  Allowed range: 1–60");
        hint.setFont(UITheme.FONT_SMALL);
        hint.setForeground(UITheme.TEXT_SECONDARY);
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        card.add(hint, gbc);

        JButton btnSave = UITheme.createPrimaryButton("Save");
        btnSave.addActionListener(e -> handleSave());

        JButton btnClear = UITheme.createSecondaryButton("Clear");
        btnClear.addActionListener(e -> clearFields());

        JButton btnBack = UITheme.createSecondaryButton("Back");
        btnBack.addActionListener(e -> dispose());

        gbc.gridy = 6;
        gbc.insets = new java.awt.Insets(16, 8, 0, 8);
        card.add(UITheme.createButtonBar(btnSave, btnClear, btnBack), gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BACKGROUND);
        wrapper.setBorder(new EmptyBorder(20, 24, 24, 24));
        wrapper.add(card, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);
    }

    private JTextField addField(JPanel panel, GridBagConstraints gbc, int row, String label) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(UITheme.createFormLabel(label), gbc);

        JTextField field = UITheme.createTextField();
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
        return field;
    }

    private void handleSave() {
        List<String> idErrors = ValidationUtil.validateFacultyId(txtId.getText());
        if (!idErrors.isEmpty()) {
            UITheme.showErrors(this, idErrors);
            return;
        }

        List<String> hourErrors = ValidationUtil.validateHours(txtHours.getText());
        if (!hourErrors.isEmpty()) {
            UITheme.showErrors(this, hourErrors);
            return;
        }

        try {
            Faculty faculty = new Faculty(
                    Integer.parseInt(txtId.getText().trim()),
                    txtName.getText().trim(),
                    txtDepartment.getText().trim(),
                    txtSubject.getText().trim(),
                    Integer.parseInt(txtHours.getText().trim()));

            List<String> errors = service.validateAndAdd(faculty);
            if (!errors.isEmpty()) {
                UITheme.showErrors(this, errors);
                return;
            }

            JOptionPane.showMessageDialog(this, "Faculty added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            if (dashboard != null) {
                dashboard.refreshStats();
            }
        } catch (NumberFormatException ex) {
            UITheme.showErrors(this, List.of("Faculty ID and hours must be valid numbers."));
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDepartment.setText("");
        txtSubject.setText("");
        txtHours.setText("");
    }
}
