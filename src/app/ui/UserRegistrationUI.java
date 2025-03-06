package app.ui;

import app.dao.UserDAO;
import app.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UserRegistrationUI extends JFrame {
    private JTextField nameField, phoneField;
    private JComboBox<String> carrierBox;
    private JButton registerButton, phoneListButton, activationHistoryButton;
    private UserDAO userDAO;

    public UserRegistrationUI() {
        userDAO = new UserDAO();

        setTitle("사용자 등록");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("이름:"));
        nameField = new JTextField("이름 입력");
        addTextFieldFocusListener(nameField);
        add(nameField);

        add(new JLabel("전화번호:"));
        phoneField = new JTextField("전화번호 입력");
        addTextFieldFocusListener(phoneField);
        add(phoneField);

        add(new JLabel("통신사:"));
        String[] carriers = {"SKT", "KT", "LGU+"};
        carrierBox = new JComboBox<>(carriers);
        add(carrierBox);

        registerButton = new JButton("등록");
        add(registerButton);

        phoneListButton = new JButton("휴대폰 목록 보기");
        add(phoneListButton);

        activationHistoryButton = new JButton("개통 내역 보기");
        add(activationHistoryButton);

        registerButton.addActionListener(e -> registerUser());
        phoneListButton.addActionListener(e -> openPhoneSelection());
        activationHistoryButton.addActionListener(e -> new ActivationHistoryUI());

        setVisible(true);
    }

    private void addTextFieldFocusListener(JTextField textField) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setText("");
            }
        });
    }

    private void registerUser() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        int carrierId = carrierBox.getSelectedIndex() + 1;

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요!", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserDTO user = new UserDTO(name, phone, carrierId);
        boolean isInserted = userDAO.addUser(user);

        if (isInserted) {
            JOptionPane.showMessageDialog(this, "사용자 등록 완료!", "성공", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "등록 실패! 전화번호 중복 확인", "에러", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openPhoneSelection() {
        String phone = phoneField.getText().trim();
        int userId = userDAO.getUserIdByPhone(phone);
        int carrierId = carrierBox.getSelectedIndex() + 1;

        if (userId != -1) {
            new PhoneSelectionUI(userId, carrierId);
        } else {
            JOptionPane.showMessageDialog(this, "등록된 사용자만 휴대폰 목록을 볼 수 있습니다!", "경고", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UserRegistrationUI();
    }
}
