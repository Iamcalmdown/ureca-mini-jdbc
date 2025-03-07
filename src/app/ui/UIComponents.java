package app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UIComponents {
    private static final Color PRIMARY_COLOR = new Color(49, 130, 246);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font BOLD_FONT = new Font("SansSerif", Font.BOLD, 16);

    public static JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setFont(DEFAULT_FONT);
        textField.setForeground(Color.GRAY);
        textField.setCaretColor(Color.BLACK);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        textField.setBackground(Color.WHITE);
        textField.setHorizontalAlignment(JTextField.LEFT);

        textField.addFocusListener(new PlaceholderFocusListener(textField, placeholder));
        return textField;
    }

    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BOLD_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        label.setForeground(TEXT_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

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
