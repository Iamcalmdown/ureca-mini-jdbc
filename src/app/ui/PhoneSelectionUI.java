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

        // 🔹 상단 패널 (버튼 영역)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 가로 배치 & 간격 조정

        // 🔹 버튼 추가 (기기변경 / 번호이동)
        JButton changeDeviceButton = UIComponents.createStyledButton("기기변경");
        JButton numberMoveButton = UIComponents.createStyledButton("번호이동");

        changeDeviceButton.setPreferredSize(new Dimension(140, 35));
        numberMoveButton.setPreferredSize(new Dimension(140, 35));

        changeDeviceButton.addActionListener(e -> phoneListPanel.loadPhoneList(true));
        numberMoveButton.addActionListener(e -> phoneListPanel.loadPhoneList(false));

        topPanel.add(changeDeviceButton);
        topPanel.add(numberMoveButton);

        // ✅ 휴대폰 목록 패널 추가
        phoneListPanel = new PhoneListPanel(userId, userCarrierId);

        // 🔹 UI 추가
        add(topPanel, BorderLayout.NORTH);
        add(phoneListPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}