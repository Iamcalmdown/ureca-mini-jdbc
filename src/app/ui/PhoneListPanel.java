package app.ui;

import app.dao.PhoneDAO;
import app.dto.PhoneDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PhoneListPanel extends JPanel {
    private JTable phoneTable;
    private PhoneDAO phoneDAO;
    private int userCarrierId;

    public PhoneListPanel(int userCarrierId) {
        this.userCarrierId = userCarrierId;
        this.phoneDAO = new PhoneDAO();
        setLayout(new BorderLayout());

        phoneTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(phoneTable);
        add(scrollPane, BorderLayout.CENTER);
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

    public int getSelectedPhoneId() {
        int selectedRow = phoneTable.getSelectedRow();
        if (selectedRow == -1) return -1;
        return Integer.parseInt((String) phoneTable.getValueAt(selectedRow, 0));
    }
}