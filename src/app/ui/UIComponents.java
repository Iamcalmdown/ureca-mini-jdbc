package app.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UIComponents {
    // 🎨 **공통 스타일 상수**
    private static final Color PRIMARY_COLOR = new Color(49, 130, 246); // 기본 블루
    private static final Color TEXT_COLOR = new Color(51, 51, 51);      // 다크 그레이
    private static final Color TABLE_HEADER_COLOR = new Color(230, 230, 230); // 연한 회색
    private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font BOLD_FONT = new Font("SansSerif", Font.BOLD, 16);

    // ✅ **스타일이 적용된 JTextField 생성**
    public static JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setFont(DEFAULT_FONT);
        textField.setForeground(Color.GRAY);
        textField.setCaretColor(Color.BLACK);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // 테두리 색상
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        textField.setBackground(Color.WHITE);
        textField.setHorizontalAlignment(JTextField.LEFT);

        textField.addFocusListener(new PlaceholderFocusListener(textField, placeholder));
        return textField;
    }

    // ✅ **스타일이 적용된 JButton 생성**
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BOLD_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    // ✅ **스타일이 적용된 JLabel 생성**
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        label.setForeground(TEXT_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    // ✅ **스타일이 적용된 JTable 생성**
    public static JTable createStyledTable(String[] columnNames, String[][] data) {
        JTable table = new JTable(data, columnNames);
        table.setFont(DEFAULT_FONT);
        table.setRowHeight(30);
        table.setGridColor(new Color(200, 200, 200));
        table.setSelectionBackground(new Color(220, 230, 250));
        table.setSelectionForeground(Color.BLACK);

        // 🎨 **테이블 헤더 스타일 적용**
        JTableHeader header = table.getTableHeader();
        header.setFont(BOLD_FONT);
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(TEXT_COLOR);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        return table;
    }

    // 🔥 **📌 Placeholder Focus Listener (코드 중복 제거)**
    private static class PlaceholderFocusListener extends FocusAdapter {
        private final JTextField textField;
        private final String placeholder;

        public PlaceholderFocusListener(JTextField textField, String placeholder) {
            this.textField = textField;
            this.placeholder = placeholder;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    }
}
