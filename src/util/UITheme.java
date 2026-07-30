package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * Shared look-and-feel for all application screens.
 */
public final class UITheme {

    public static final Color PRIMARY = new Color(30, 58, 95);
    public static final Color PRIMARY_LIGHT = new Color(52, 86, 130);
    public static final Color ACCENT = new Color(41, 128, 185);
    public static final Color ACCENT_HOVER = new Color(31, 97, 141);
    public static final Color BACKGROUND = new Color(244, 246, 249);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(44, 62, 80);
    public static final Color TEXT_SECONDARY = new Color(127, 140, 141);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color DANGER = new Color(192, 57, 43);
    public static final Color BORDER = new Color(220, 224, 230);

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_STAT_VALUE = new Font("Segoe UI", Font.BOLD, 28);

    private UITheme() {
    }

    public static void applyFrameDefaults(javax.swing.JFrame frame) {
        frame.getContentPane().setBackground(BACKGROUND);
        frame.setLocationRelativeTo(null);
    }

    public static JPanel createHeaderPanel(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY);
        panel.setBorder(new EmptyBorder(20, 24, 20, 24));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(Color.WHITE);

        panel.add(lblTitle, BorderLayout.NORTH);

        if (subtitle != null && !subtitle.isEmpty()) {
            JLabel lblSub = new JLabel(subtitle);
            lblSub.setFont(FONT_BODY);
            lblSub.setForeground(new Color(200, 215, 230));
            lblSub.setBorder(new EmptyBorder(6, 0, 0, 0));
            panel.add(lblSub, BorderLayout.SOUTH);
        }

        return panel;
    }

    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(20, 24, 20, 24)));
        return card;
    }

    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_BODY);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT_BODY);
        field.setPreferredSize(new Dimension(220, 32));
        return field;
    }

    public static JButton createPrimaryButton(String text) {
        return createButton(text, ACCENT, Color.WHITE);
    }

    public static JButton createSecondaryButton(String text) {
        return createButton(text, new Color(236, 240, 241), TEXT_PRIMARY);
    }

    public static JButton createDangerButton(String text) {
        return createButton(text, DANGER, Color.WHITE);
    }

    public static JButton createNavButton(String text) {
        JButton btn = createButton(text, CARD, TEXT_PRIMARY);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(12, 16, 12, 16)));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return btn;
    }

    private static JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_SUBHEADING);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    public static JPanel createButtonBar(JComponent... buttons) {
        JPanel bar = new JPanel();
        bar.setBackground(CARD);
        bar.setBorder(new EmptyBorder(12, 0, 0, 0));
        for (JComponent btn : buttons) {
            bar.add(btn);
        }
        return bar;
    }

    public static JPanel createStatCard(String label, String value, Color accentColor) {
        JPanel card = createCardPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(160, 100));

        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(FONT_STAT_VALUE);
        lblValue.setForeground(accentColor);

        JLabel lblLabel = new JLabel(label, SwingConstants.CENTER);
        lblLabel.setFont(FONT_SMALL);
        lblLabel.setForeground(TEXT_SECONDARY);

        JPanel accent = new JPanel();
        accent.setBackground(accentColor);
        accent.setPreferredSize(new Dimension(4, 100));

        card.add(accent, BorderLayout.WEST);
        card.add(lblValue, BorderLayout.CENTER);
        card.add(lblLabel, BorderLayout.SOUTH);

        return card;
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(32);
        table.setGridColor(BORDER);
        table.setSelectionBackground(new Color(214, 234, 248));
        table.setSelectionForeground(TEXT_PRIMARY);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_SUBHEADING);
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static JScrollPane createTableScrollPane(JTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        return scroll;
    }

    public static GridBagConstraints formConstraints(int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = row;
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    public static void showErrors(Component parent, java.util.List<String> errors) {
        if (errors != null && !errors.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    parent,
                    String.join("\n", errors),
                    "Validation Error",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
}
