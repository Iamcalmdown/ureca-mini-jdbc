package app.ui;

import javax.swing.*;
import java.awt.*;

public class PhoneSelectionUI extends JFrame {
    private PhoneListPanel phoneListPanel;

    public PhoneSelectionUI(int userId, int userCarrierId) { // ✅ userId 전달
        setTitle("휴대폰 선택");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ✅ 배경 색상 설정
        getContentPane().setBackground(new Color(245, 245, 245));

        // 🔹 상단 패널 (버튼 영역)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        topPanel.setBackground(new Color(245, 245, 245));

        // 🔹 라벨 추가
        topPanel.add(UIComponents.createLabel("휴대폰 선택"));
        topPanel.add(Box.createVerticalStrut(15));

        // 🔹 버튼 추가 (기기변경 / 번호이동)
        JButton changeDeviceButton = UIComponents.createStyledButton("기기변경");
        JButton numberMoveButton = UIComponents.createStyledButton("번호이동");

        changeDeviceButton.addActionListener(e -> phoneListPanel.loadPhoneList(true));
        numberMoveButton.addActionListener(e -> phoneListPanel.loadPhoneList(false));

        topPanel.add(changeDeviceButton);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(numberMoveButton);

        // ✅ 휴대폰 목록 패널 추가
        phoneListPanel = new PhoneListPanel(userId, userCarrierId);

        // 🔹 UI 추가
        add(topPanel, BorderLayout.NORTH);
        add(phoneListPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}