package app.ui;

import app.dao.UserDAO;
import app.dto.UserDTO;

import javax.swing.*;
import java.awt.*;

public class UserRegistrationUI extends JFrame {
    private JTextField nameField, phoneField;
    private JComboBox<String> carrierBox;
    private JButton registerButton;
    private UserDAO userDAO;

    public UserRegistrationUI() {
        userDAO = new UserDAO();

        setTitle("사용자 등록");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("이름:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("전화번호:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("통신사:"));
        String[] carriers = {"SKT", "KT", "LGU+"};
        carrierBox = new JComboBox<>(carriers);
        add(carrierBox);

        registerButton = new JButton("등록");
        add(registerButton);

        registerButton.addActionListener(e -> registerUser());

        setVisible(true);
    }

    private void registerUser() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        int carrierId = carrierBox.getSelectedIndex() + 1; // 1: SKT, 2: KT, 3: LGU+

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요!", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserDTO user = new UserDTO(name, phone, carrierId);
        boolean isInserted = userDAO.addUser(user);

        if (isInserted) {
            JOptionPane.showMessageDialog(this, "사용자 등록 완료!", "성공", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // 현재 창 닫기
            new PhoneSelectionUI(carrierId); // 개통 선택 화면으로 이동
        } else {
            JOptionPane.showMessageDialog(this, "등록 실패! 전화번호 중복 확인", "에러", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UserRegistrationUI();
    }
}