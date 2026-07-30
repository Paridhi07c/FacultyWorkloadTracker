package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import service.FacultyService;
import service.ReportService;
import service.WorkloadService;
import util.UITheme;

public class ReportFrame extends JFrame {

    private final FacultyService service = FacultyService.getInstance();
    private final WorkloadService workloadService = service.getWorkloadService();
    private final ReportService reportService = service.getReportService();
    private final Dashboard dashboard;

    private JTextArea previewArea;

    public ReportFrame(Dashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Reports & Export");
        setSize(640, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UITheme.applyFrameDefaults(this);
        buildUI();
        previewArea.setText(workloadService.buildSummaryReport());
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(UITheme.createHeaderPanel("Reports & Export", "Generate and export workload reports"), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout(16, 0));
        main.setBackground(UITheme.BACKGROUND);
        main.setBorder(new EmptyBorder(20, 24, 24, 24));

        JPanel actions = new JPanel(new GridLayout(0, 1, 0, 8));
        actions.setBackground(UITheme.BACKGROUND);
        actions.setPreferredSize(new java.awt.Dimension(200, 0));

        actions.add(exportButton("Export Faculty CSV", this::exportFacultyCsv));
        actions.add(exportButton("Export Workload Report", this::exportWorkloadReport));
        actions.add(exportButton("Export Department Report", this::exportDepartmentReport));
        actions.add(exportButton("Preview Summary", () ->
                previewArea.setText(workloadService.buildSummaryReport())));

        JButton btnBack = UITheme.createSecondaryButton("Back");
        btnBack.addActionListener(e -> dispose());
        actions.add(btnBack);

        previewArea = new JTextArea();
        previewArea.setEditable(false);
        previewArea.setFont(UITheme.FONT_BODY);
        previewArea.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(previewArea);
        scroll.setBorder(javax.swing.BorderFactory.createLineBorder(UITheme.BORDER));

        JPanel previewPanel = UITheme.createCardPanel();
        previewPanel.setLayout(new BorderLayout());
        JLabel lblPreview = new JLabel("Report Preview");
        lblPreview.setFont(UITheme.FONT_HEADING);
        lblPreview.setForeground(UITheme.TEXT_PRIMARY);
        lblPreview.setBorder(new EmptyBorder(0, 0, 8, 0));
        previewPanel.add(lblPreview, BorderLayout.NORTH);
        previewPanel.add(scroll, BorderLayout.CENTER);

        main.add(actions, BorderLayout.WEST);
        main.add(previewPanel, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }

    private JButton exportButton(String text, Runnable action) {
        JButton btn = UITheme.createNavButton(text);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private void exportFacultyCsv() {
        export(() -> reportService.exportFacultyCsv(), "Faculty CSV exported to:\n");
    }

    private void exportWorkloadReport() {
        export(() -> reportService.exportWorkloadReport(), "Workload report exported to:\n");
    }

    private void exportDepartmentReport() {
        export(() -> reportService.exportDepartmentReport(), "Department report exported to:\n");
    }

    private void export(java.util.concurrent.Callable<String> exporter, String messagePrefix) {
        try {
            String path = exporter.call();
            JOptionPane.showMessageDialog(this,
                    messagePrefix + path,
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            if (dashboard != null) {
                dashboard.refreshStats();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Export failed: " + ex.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
