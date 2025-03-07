package app.ui;

import app.dao.UserDAO;
import app.dto.UserDTO;

import javax.swing.*;
import java.awt.*;

public class UserRegistrationUI extends JFrame {
    private JTextField nameField, phoneField;
    private JComboBox<String> carrierBox;
    private JButton registerButton, phoneListButton, activationHistoryButton;
    private UserDAO userDAO;

    public UserRegistrationUI() {
        userDAO = new UserDAO();

        setTitle("사용자 등록");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ✅ 메인 패널 설정 (BoxLayout 활용)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ✅ 제목 라벨
        JLabel titleLabel = UIComponents.createLabel("👤 사용자 등록");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(15));

        // ✅ 이름 입력 필드
        nameField = UIComponents.createStyledTextField("이름 입력");
        mainPanel.add(nameField);
        mainPanel.add(Box.createVerticalStrut(10));

        // ✅ 전화번호 입력 필드
        phoneField = UIComponents.createStyledTextField("전화번호 입력");
        mainPanel.add(phoneField);
        mainPanel.add(Box.createVerticalStrut(10));

        // ✅ 통신사 선택 (JComboBox)
        carrierBox = new JComboBox<>(new String[]{"SKT", "KT", "LGU+"});
        carrierBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        mainPanel.add(carrierBox);
        mainPanel.add(Box.createVerticalStrut(20));

        // ✅ 등록 버튼
        registerButton = UIComponents.createStyledButton("등록");
        registerButton.addActionListener(e -> registerUser());
        mainPanel.add(registerButton);
        mainPanel.add(Box.createVerticalStrut(10));

        // ✅ 휴대폰 목록 버튼
        phoneListButton = UIComponents.createStyledButton("휴대폰 목록 보기");
        phoneListButton.addActionListener(e -> openPhoneSelection());
        mainPanel.add(phoneListButton);
        mainPanel.add(Box.createVerticalStrut(10));

        // ✅ 개통 내역 버튼
        activationHistoryButton = UIComponents.createStyledButton("개통 내역 보기");
        activationHistoryButton.addActionListener(e -> new ActivationHistoryUI());
        mainPanel.add(activationHistoryButton);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // 📌 사용자 등록 기능
    private void registerUser() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        int carrierId = carrierBox.getSelectedIndex() + 1;

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요!", "⚠ 경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserDTO user = new UserDTO(name, phone, carrierId);
        boolean isInserted = userDAO.addUser(user);

        if (isInserted) {
            JOptionPane.showMessageDialog(this, "✅ 사용자 등록 완료!", "성공", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "❌ 등록 실패! 전화번호 중복 확인", "에러", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 📌 휴대폰 목록 보기 기능
    private void openPhoneSelection() {
        String phone = phoneField.getText().trim();
        int userId = userDAO.getUserIdByPhone(phone);
        int carrierId = carrierBox.getSelectedIndex() + 1;

        if (userId != -1) {
            new PhoneSelectionUI(userId, carrierId);
        } else {
            JOptionPane.showMessageDialog(this, "⚠ 등록된 사용자만 휴대폰 목록을 볼 수 있습니다!", "경고", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UserRegistrationUI();
    }
}