package app.ui;

import javax.swing.*;
import java.awt.*;

public class PhoneSelectionUI extends JFrame {
    private PhoneListPanel phoneListPanel;

    public PhoneSelectionUI(int userId, int userCarrierId) {
        setTitle("휴대폰 선택");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // 버튼 (기기변경 / 번호이동)
        JButton changeDeviceButton = UIComponents.createStyledButton("기기변경");
        JButton numberMoveButton = UIComponents.createStyledButton("번호이동");

        changeDeviceButton.setPreferredSize(new Dimension(140, 35));
        numberMoveButton.setPreferredSize(new Dimension(140, 35));

        changeDeviceButton.addActionListener(e -> phoneListPanel.loadPhoneList(true));
        numberMoveButton.addActionListener(e -> phoneListPanel.loadPhoneList(false));

        topPanel.add(changeDeviceButton);
        topPanel.add(numberMoveButton);

        phoneListPanel = new PhoneListPanel(userId, userCarrierId);

        add(topPanel, BorderLayout.NORTH);
        add(phoneListPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}