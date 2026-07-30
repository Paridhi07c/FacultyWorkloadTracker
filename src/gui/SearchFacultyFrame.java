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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Faculty;
import service.FacultyService;
import util.UITheme;
import util.ValidationUtil;

public class SearchFacultyFrame extends JFrame {

    private JTextField txtId;
    private JTextArea resultArea;
    private final FacultyService service = FacultyService.getInstance();

    public SearchFacultyFrame() {
        setTitle("Search Faculty");
        setSize(540, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UITheme.applyFrameDefaults(this);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(UITheme.createHeaderPanel("Search Faculty", "Find faculty by ID"), BorderLayout.NORTH);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(24, 32, 24, 32));

        GridBagConstraints gbc = UITheme.formConstraints(0);

        gbc.gridx = 0;
        card.add(UITheme.createFormLabel("Faculty ID"), gbc);

        txtId = UITheme.createTextField();
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(txtId, gbc);

        JButton btnSearch = UITheme.createPrimaryButton("Search");
        btnSearch.addActionListener(e -> handleSearch());
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new java.awt.Insets(12, 8, 8, 8);
        card.add(btnSearch, gbc);

        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(UITheme.FONT_BODY);
        resultArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBorder(javax.swing.BorderFactory.createLineBorder(UITheme.BORDER));

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        card.add(scroll, gbc);

        JButton btnBack = UITheme.createSecondaryButton("Back");
        btnBack.addActionListener(e -> dispose());
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(btnBack, gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BACKGROUND);
        wrapper.setBorder(new EmptyBorder(20, 24, 24, 24));
        wrapper.add(card, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);

        getRootPane().setDefaultButton(btnSearch);
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
            resultArea.setText(faculty.toString());
        } else {
            resultArea.setText("Faculty not found for ID: " + id);
        }
    }
}
