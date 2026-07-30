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

import service.FacultyService;
import util.UITheme;
import util.ValidationUtil;

public class DeleteFacultyFrame extends JFrame {

    private JTextField txtId;
    private final FacultyService service = FacultyService.getInstance();
    private final Dashboard dashboard;

    public DeleteFacultyFrame() {
        this(null);
    }

    public DeleteFacultyFrame(Dashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Delete Faculty");
        setSize(480, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UITheme.applyFrameDefaults(this);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(UITheme.createHeaderPanel("Delete Faculty", "Remove a faculty record by ID"), BorderLayout.NORTH);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(32, 32, 32, 32));

        GridBagConstraints gbc = UITheme.formConstraints(0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        card.add(UITheme.createFormLabel("Faculty ID"), gbc);

        txtId = UITheme.createTextField();
        gbc.gridx = 1;
        gbc.weightx = 1;
        card.add(txtId, gbc);

        JLabel warning = new JLabel("This action cannot be undone.");
        warning.setFont(UITheme.FONT_SMALL);
        warning.setForeground(UITheme.DANGER);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new java.awt.Insets(12, 8, 8, 8);
        card.add(warning, gbc);

        JButton btnDelete = UITheme.createDangerButton("Delete");
        btnDelete.addActionListener(e -> handleDelete());

        JButton btnBack = UITheme.createSecondaryButton("Back");
        btnBack.addActionListener(e -> dispose());

        gbc.gridy = 2;
        card.add(UITheme.createButtonBar(btnDelete, btnBack), gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BACKGROUND);
        wrapper.setBorder(new EmptyBorder(20, 24, 24, 24));
        wrapper.add(card, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);
    }

    private void handleDelete() {
        List<String> errors = ValidationUtil.validateFacultyId(txtId.getText());
        if (!errors.isEmpty()) {
            UITheme.showErrors(this, errors);
            return;
        }

        int id = Integer.parseInt(txtId.getText().trim());

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete faculty ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted = service.deleteFaculty(id);

        if (deleted) {
            JOptionPane.showMessageDialog(this, "Faculty deleted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            txtId.setText("");
            if (dashboard != null) {
                dashboard.refreshStats();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Faculty not found.",
                    "Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }
}
