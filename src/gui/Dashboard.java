package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import service.FacultyService;
import service.WorkloadService;
import util.UITheme;

public class Dashboard extends JFrame {

    private final FacultyService service = FacultyService.getInstance();
    private final WorkloadService workloadService = service.getWorkloadService();

    private JLabel lblTotalFaculty;
    private JLabel lblTotalHours;
    private JLabel lblAvgHours;
    private JLabel lblDepartments;
    private DefaultTableModel deptModel;

    public Dashboard() {
        setTitle("Faculty Workload Tracker - Dashboard");
        setSize(960, 640);
        setMinimumSize(new Dimension(860, 580));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyFrameDefaults(this);
        buildUI();
        refreshStats();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        add(UITheme.createHeaderPanel(
                "Dashboard",
                "Overview of faculty workload and quick actions"), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout(16, 0));
        main.setBackground(UITheme.BACKGROUND);
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        main.add(buildSidebar(), BorderLayout.WEST);
        main.add(buildContent(), BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0, 1, 0, 8));
        sidebar.setBackground(UITheme.BACKGROUND);
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(0, 0, 0, 8));

        sidebar.add(navButton("Add Faculty", () -> new AddFacultyFrame(this)));
        sidebar.add(navButton("View Faculty", () -> new ViewFacultyFrame(this)));
        sidebar.add(navButton("Search Faculty", () -> new SearchFacultyFrame()));
        sidebar.add(navButton("Update Faculty", () -> new UpdateFacultyFrame()));
        sidebar.add(navButton("Delete Faculty", () -> new DeleteFacultyFrame(this)));
        sidebar.add(navButton("Workload Report", this::showWorkloadDialog));
        sidebar.add(navButton("Export Reports", () -> new ReportFrame(this)));

        JButton btnRefresh = UITheme.createNavButton("Refresh Dashboard");
        btnRefresh.addActionListener(e -> refreshStats());
        sidebar.add(btnRefresh);

        JButton btnExit = UITheme.createDangerButton("Logout & Exit");
        btnExit.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
        sidebar.add(btnExit);

        return sidebar;
    }

    private JButton navButton(String text, Runnable action) {
        JButton btn = UITheme.createNavButton(text);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(UITheme.BACKGROUND);

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 12, 0));
        statsRow.setBackground(UITheme.BACKGROUND);

        lblTotalFaculty = statValueLabel("0");
        lblTotalHours = statValueLabel("0");
        lblAvgHours = statValueLabel("0");
        lblDepartments = statValueLabel("0");

        statsRow.add(wrapStatCard("Total Faculty", lblTotalFaculty, UITheme.ACCENT));
        statsRow.add(wrapStatCard("Total Hours/Week", lblTotalHours, UITheme.SUCCESS));
        statsRow.add(wrapStatCard("Avg Hours/Faculty", lblAvgHours, UITheme.WARNING));
        statsRow.add(wrapStatCard("Departments", lblDepartments, UITheme.PRIMARY_LIGHT));

        content.add(statsRow, BorderLayout.NORTH);

        JPanel deptPanel = UITheme.createCardPanel();
        deptPanel.setLayout(new BorderLayout(0, 12));

        JLabel deptTitle = new JLabel("Workload by Department");
        deptTitle.setFont(UITheme.FONT_HEADING);
        deptTitle.setForeground(UITheme.TEXT_PRIMARY);
        deptPanel.add(deptTitle, BorderLayout.NORTH);

        deptModel = new DefaultTableModel(new String[]{"Department", "Total Hours", "Faculty Count"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable deptTable = new JTable(deptModel);
        UITheme.styleTable(deptTable);
        deptPanel.add(UITheme.createTableScrollPane(deptTable), BorderLayout.CENTER);

        content.add(deptPanel, BorderLayout.CENTER);

        return content;
    }

    private JLabel statValueLabel(String value) {
        JLabel lbl = new JLabel(value, JLabel.CENTER);
        lbl.setFont(UITheme.FONT_STAT_VALUE);
        return lbl;
    }

    private JPanel wrapStatCard(String label, JLabel valueLabel, java.awt.Color color) {
        JPanel card = UITheme.createCardPanel();
        card.setLayout(new BorderLayout());

        valueLabel.setForeground(color);
        card.add(valueLabel, BorderLayout.CENTER);

        JLabel lbl = new JLabel(label, JLabel.CENTER);
        lbl.setFont(UITheme.FONT_SMALL);
        lbl.setForeground(UITheme.TEXT_SECONDARY);
        card.add(lbl, BorderLayout.SOUTH);

        JPanel accent = new JPanel();
        accent.setBackground(color);
        accent.setPreferredSize(new Dimension(4, 80));
        card.add(accent, BorderLayout.WEST);

        return card;
    }

    public void refreshStats() {
        service.refreshFromStorage();

        lblTotalFaculty.setText(String.valueOf(workloadService.getFacultyCount()));
        lblTotalHours.setText(String.valueOf(workloadService.calculateTotalWorkload()));
        lblAvgHours.setText(String.format("%.1f", workloadService.calculateAverageWorkload()));
        lblDepartments.setText(String.valueOf(workloadService.getDepartmentCount()));

        deptModel.setRowCount(0);
        for (var entry : workloadService.getWorkloadByDepartment().entrySet()) {
            int count = 0;
            for (var f : service.getAllFaculty()) {
                if (f.getDepartment().equalsIgnoreCase(entry.getKey())) {
                    count++;
                }
            }
            deptModel.addRow(new Object[]{entry.getKey(), entry.getValue(), count});
        }
    }

    private void showWorkloadDialog() {
        JOptionPane.showMessageDialog(this,
                workloadService.buildSummaryReport(),
                "Workload Summary",
                JOptionPane.INFORMATION_MESSAGE);
        refreshStats();
    }
}
