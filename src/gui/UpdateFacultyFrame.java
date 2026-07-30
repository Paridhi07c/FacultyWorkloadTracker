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

public class UpdateFacultyFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtDepartment;
    private JTextField txtSubject;
    private JTextField txtHours;

    private final FacultyService service = FacultyService.getInstance();

    public UpdateFacultyFrame() {
        setTitle("Update Faculty");
        setSize(540, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UITheme.applyFrameDefaults(this);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(UITheme.createHeaderPanel("Update Faculty", "Search by ID and modify details"), BorderLayout.NORTH);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(24, 32, 24, 32));

        GridBagConstraints gbc = UITheme.formConstraints(0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(UITheme.createFormLabel("Faculty ID"), gbc);

        txtId = UITheme.createTextField();
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        card.add(txtId, gbc);

        JButton btnSearch = UITheme.createSecondaryButton("Search");
        btnSearch.addActionListener(e -> handleSearch());
        gbc.gridx = 2;
        gbc.weightx = 0;
        card.add(btnSearch, gbc);

        txtName = addField(card, gbc, 1, "Faculty Name *");
        txtDepartment = addField(card, gbc, 2, "Department *");
        txtSubject = addField(card, gbc, 3, "Subject *");
        txtHours = addField(card, gbc, 4, "Hours / Week *");

        JButton btnUpdate = UITheme.createPrimaryButton("Update");
        btnUpdate.addActionListener(e -> handleUpdate());

        JButton btnBack = UITheme.createSecondaryButton("Back");
        btnBack.addActionListener(e -> dispose());

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new java.awt.Insets(16, 8, 0, 8);
        card.add(UITheme.createButtonBar(btnUpdate, btnBack), gbc);

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
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panel.add(field, gbc);
        return field;
    }

    private void handleSearch() {
        List<String> errors = ValidationUtil.validateFacultyId(txtId.getText());
        if (!errors.isEmpty()) {
            UITheme.showErrors(this, errors);
            return;
        }

        int id = Integer.parseInt(txtId.getText().trim());
        Faculty faculty = service.searchFaculty(id);

        if (faculty != null) {
            txtName.setText(faculty.getFacultyName());
            txtDepartment.setText(faculty.getDepartment());
            txtSubject.setText(faculty.getSubject());
            txtHours.setText(String.valueOf(faculty.getHoursPerWeek()));
        } else {
            JOptionPane.showMessageDialog(this, "Faculty not found.",
                    "Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleUpdate() {
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
            int id = Integer.parseInt(txtId.getText().trim());
            List<String> errors = service.validateAndUpdate(
                    id,
                    txtName.getText(),
                    txtDepartment.getText(),
                    txtSubject.getText(),
                    Integer.parseInt(txtHours.getText().trim()));

            if (!errors.isEmpty()) {
                UITheme.showErrors(this, errors);
                return;
            }

            JOptionPane.showMessageDialog(this, "Faculty updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            UITheme.showErrors(this, List.of("Hours must be a valid number."));
        }
    }
}
