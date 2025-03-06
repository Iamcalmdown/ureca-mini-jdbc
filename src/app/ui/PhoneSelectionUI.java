package app.ui;

import javax.swing.*;
import java.awt.*;

public class PhoneSelectionUI extends JFrame {
    private PhoneListPanel phoneListPanel;

    public PhoneSelectionUI(int userCarrierId) {
        setTitle("휴대폰 선택");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        phoneListPanel = new PhoneListPanel(userCarrierId);
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