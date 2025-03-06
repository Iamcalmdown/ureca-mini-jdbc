package app.ui;

import app.dao.PhoneDAO;
import app.dao.ActivationDAO;
import app.dto.PhoneDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PhoneListPanel extends JPanel {
    private JTable phoneTable;
    private PhoneDAO phoneDAO;
    private ActivationDAO activationDAO; // ✅ 개통 DAO 추가
    private int userId; // ✅ userId 추가
    private int userCarrierId;
    private JButton activateButton; // ✅ 개통 버튼 추가

    public PhoneListPanel(int userId, int userCarrierId) { // ✅ userId 추가
        this.userId = userId;
        this.userCarrierId = userCarrierId;
        this.phoneDAO = new PhoneDAO();
        this.activationDAO = new ActivationDAO();

        setLayout(new BorderLayout());

        phoneTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(phoneTable);
        add(scrollPane, BorderLayout.CENTER);

        // ✅ 개통 버튼 추가
        activateButton = new JButton("개통");
        activateButton.addActionListener(e -> activateSelectedPhone());
        add(activateButton, BorderLayout.SOUTH);
    }

    public void loadPhoneList(boolean isDeviceChange) {
        List<PhoneDTO> phones = isDeviceChange ?
                phoneDAO.getPhonesByCarrier(userCarrierId) :
                phoneDAO.getPhonesByDifferentCarrier(userCarrierId);

        String[] columnNames = {"ID", "모델명", "통신사", "재고"};
        String[][] data = new String[phones.size()][4];

        for (int i = 0; i < phones.size(); i++) {
            PhoneDTO phone = phones.get(i);
            data[i][0] = String.valueOf(phone.getPhoneId());
            data[i][1] = phone.getModelName();
            data[i][2] = phone.getCarrierName();
            data[i][3] = String.valueOf(phone.getStockCount());
        }

        phoneTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void activateSelectedPhone() {
        int selectedPhoneId = getSelectedPhoneId();
        if (selectedPhoneId == -1) {
            JOptionPane.showMessageDialog(this, "개통할 휴대폰을 선택하세요!", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ✅ 개통 처리 (userId, phoneId 전달)
        activationDAO.activateUser(userId, selectedPhoneId);

        JOptionPane.showMessageDialog(this, "개통이 완료되었습니다!", "성공", JOptionPane.INFORMATION_MESSAGE);
    }

    public int getSelectedPhoneId() {
        int selectedRow = phoneTable.getSelectedRow();
        if (selectedRow == -1) return -1;
        return Integer.parseInt((String) phoneTable.getValueAt(selectedRow, 0));
    }
}