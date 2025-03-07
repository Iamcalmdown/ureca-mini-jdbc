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
    private ActivationDAO activationDAO;
    private int userId;
    private int userCarrierId;
    private JButton activateButton;
    private boolean isDeviceChange = true; // 기본값: 기기변경 모드

    public PhoneListPanel(int userId, int userCarrierId) {
        this.userId = userId;
        this.userCarrierId = userCarrierId;
        this.phoneDAO = new PhoneDAO();
        this.activationDAO = new ActivationDAO();

        setLayout(new BorderLayout());

        // 기본 테이블 생성
        String[] columnNames = {"모델명", "통신사", "재고"};
        phoneTable = new JTable(new Object[0][3], columnNames);
        JScrollPane scrollPane = new JScrollPane(phoneTable);
        add(scrollPane, BorderLayout.CENTER);

        // 개통 버튼 추가
        JPanel bottomPanel = new JPanel();
        activateButton = new JButton("개통");
        activateButton.addActionListener(e -> activateSelectedPhone());

        bottomPanel.add(activateButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 휴대폰 목록 불러오기 (isDeviceChange 값에 따라 필터링)
    public void loadPhoneList(boolean isDeviceChange) {
        this.isDeviceChange = isDeviceChange;
        List<PhoneDTO> phones = phoneDAO.getPhones(userCarrierId, isDeviceChange);

        String[] columnNames = {"모델명", "통신사", "재고"};
        String[][] data = new String[phones.size()][3];

        for (int i = 0; i < phones.size(); i++) {
            PhoneDTO phone = phones.get(i);
            data[i][0] = phone.getModelName();
            data[i][1] = phone.getCarrierName();
            data[i][2] = String.valueOf(phone.getStockCount());
        }

        phoneTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    // 개통 로직 (재고 확인 및 메시지 추가)
    private void activateSelectedPhone() {
        int selectedPhoneId = getSelectedPhoneId();
        if (selectedPhoneId == -1) {
            JOptionPane.showMessageDialog(this, "개통할 휴대폰을 선택하세요!", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 개통 전에 재고 감소 시도
        int updatedStock = phoneDAO.updateStock(selectedPhoneId, -1);
        if (updatedStock == -1) {
            JOptionPane.showMessageDialog(this, "해당 기기는 재고가 부족하여 개통할 수 없습니다.", "개통 실패", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 개통 진행
        activationDAO.activateUser(userId, selectedPhoneId);
        JOptionPane.showMessageDialog(this, "개통이 완료되었습니다! 남은 재고: " + updatedStock, "성공", JOptionPane.INFORMATION_MESSAGE);

        // 개통 후 목록 갱신 (이전 모드 유지)
        loadPhoneList(isDeviceChange);
    }

    // 선택된 휴대폰 ID 가져오기
    public int getSelectedPhoneId() {
        int selectedRow = phoneTable.getSelectedRow();
        if (selectedRow == -1) return -1;

        String modelName = (String) phoneTable.getValueAt(selectedRow, 0);
        return phoneDAO.getPhoneIdByModel(modelName);
    }
}