package app.ui;

import app.dao.UserDAO;
import app.dto.UserDTO;

import javax.swing.*;
import java.awt.*;

public class UserRegistrationUI extends JFrame {
    private JTextField nameField, phoneField;
    private JComboBox<String> carrierBox;
    private JButton registerButton, activationHistoryButton; // 개통 내역 버튼 추가
    private UserDAO userDAO;

    public UserRegistrationUI() {
        userDAO = new UserDAO();

        setTitle("사용자 등록");
        setSize(300, 300); // 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10)); // 행 추가

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

        // 개통 내역 보기 버튼 추가
        activationHistoryButton = new JButton("개통 내역 보기");
        add(activationHistoryButton);

        // 버튼 리스너 추가
        registerButton.addActionListener(e -> registerUser());
        activationHistoryButton.addActionListener(e -> new ActivationHistoryUI()); // 개통 내역 보기 창 열기

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
            // ✅ 새로 등록된 사용자의 ID 가져오기
            int userId = userDAO.getUserIdByPhone(phone);

            if (userId != -1) {
                JOptionPane.showMessageDialog(this, "사용자 등록 완료!", "성공", JOptionPane.INFORMATION_MESSAGE);
                // ✅ 기존 창을 닫지 않고 새 창을 띄움
                SwingUtilities.invokeLater(() -> new PhoneSelectionUI(userId, carrierId));
            } else {
                JOptionPane.showMessageDialog(this, "사용자 정보를 찾을 수 없습니다!", "에러", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "등록 실패! 전화번호 중복 확인", "에러", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UserRegistrationUI();
    }
}
