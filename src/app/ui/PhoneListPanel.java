package app.ui;

import app.dao.PhoneDAO;
import app.dao.ActivationDAO;
import app.dto.PhoneDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PhoneListPanel extends JPanel {
    private JTable phoneTable;
    private DefaultTableModel tableModel;
    private PhoneDAO phoneDAO;
    private ActivationDAO activationDAO;
    private int userId;
    private int userCarrierId;
    private boolean isDeviceChange = true; // 기본값: 기기변경 모드

    public PhoneListPanel(int userId, int userCarrierId) {
        this.userId = userId;
        this.userCarrierId = userCarrierId;
        this.phoneDAO = new PhoneDAO();
        this.activationDAO = new ActivationDAO();

        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "모델명", "통신사", "재고"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        phoneTable = new JTable(tableModel);
        phoneTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // ID 컬럼 숨기기
        phoneTable.getColumnModel().getColumn(0).setMinWidth(0);
        phoneTable.getColumnModel().getColumn(0).setMaxWidth(0);
        phoneTable.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scrollPane = new JScrollPane(phoneTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton activateButton = new JButton("개통");
        activateButton.addActionListener(e -> activateSelectedPhone());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(activateButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 목록 가져오기
    public void loadPhoneList(boolean isDeviceChange) {
        this.isDeviceChange = isDeviceChange;
        List<PhoneDTO> phones = phoneDAO.getPhones(userCarrierId, isDeviceChange);

        tableModel.setRowCount(0);
        for (PhoneDTO phone : phones) {
            tableModel.addRow(new Object[]{
                    phone.getPhoneId(),
                    phone.getModelName(),
                    phone.getCarrierName(),
                    phone.getStockCount()
            });
        }
    }

    // 재고 확인 및 메시지 추가
    private void activateSelectedPhone() {
        int selectedRow = phoneTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "개통할 휴대폰을 선택하세요!", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int phoneId = (int) tableModel.getValueAt(selectedRow, 0);
        int stock = (int) tableModel.getValueAt(selectedRow, 3);

        if (stock <= 0) {
            JOptionPane.showMessageDialog(this, "해당 기기는 재고가 부족합니다.", "개통 실패", JOptionPane.ERROR_MESSAGE);
            return;
        }

        activationDAO.activateUser(userId, phoneId);
        phoneDAO.updateStock(phoneId, -1); // 개통 후 재고 감소
        JOptionPane.showMessageDialog(this, "개통 완료! 남은 재고: " + (stock - 1), "성공", JOptionPane.INFORMATION_MESSAGE);

        loadPhoneList(isDeviceChange);
    }
}