package app.ui;

import javax.swing.*;
import java.awt.*;

public class PhoneSelectionUI extends JFrame {
    private PhoneListPanel phoneListPanel;

    public PhoneSelectionUI(int userId, int userCarrierId) { // ✅ userId 추가
        setTitle("휴대폰 선택");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        phoneListPanel = new PhoneListPanel(userId, userCarrierId); // ✅ userId 전달
        add(phoneListPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton changeDeviceButton = new JButton("기기변경");
        JButton numberMoveButton = new JButton("번호이동");

        changeDeviceButton.addActionListener(e -> phoneListPanel.loadPhoneList(true));
        numberMoveButton.addActionListener(e -> phoneListPanel.loadPhoneList(false));

        buttonPanel.add(changeDeviceButton);
        buttonPanel.add(numberMoveButton);
        add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);
    }
}