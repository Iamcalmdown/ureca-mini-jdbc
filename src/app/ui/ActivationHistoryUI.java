package app.ui;

import app.dao.ActivationDAO;
import app.dto.ActivationDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActivationHistoryUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton cancelActivationButton;
    private ActivationDAO activationDAO;

    public ActivationHistoryUI() {
        activationDAO = new ActivationDAO();

        setTitle("개통 내역");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("📜 개통 내역", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"개통 ID", "사용자 ID", "휴대폰 ID", "이름", "전화번호", "기종", "통신사", "개통 날짜"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        cancelActivationButton = new JButton("개통 취소");
        cancelActivationButton.addActionListener(e -> cancelActivation());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(cancelActivationButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadActivationHistory();
        setVisible(true);
    }

    private void loadActivationHistory() {
        tableModel.setRowCount(0);

        List<ActivationDTO> activations = activationDAO.getActivationHistory();

        for (ActivationDTO activation : activations) {
            tableModel.addRow(new Object[]{
                    activation.getActivationId(),
                    activation.getUserId(),
                    activation.getPhoneId(),
                    activation.getUserName(),
                    activation.getPhoneNumber(),
                    activation.getModelName(),
                    activation.getPreviousCarrier() + " → " + activation.getNewCarrier(),
                    activation.getActivationDate()
            });
        }

        table.setModel(tableModel);

        SwingUtilities.invokeLater(() -> {
            hideColumn(0);
            hideColumn(1);
            hideColumn(2);
            table.repaint();
        });
    }

    private void hideColumn(int index) {
        table.getColumnModel().getColumn(index).setMinWidth(0);
        table.getColumnModel().getColumn(index).setMaxWidth(0);
        table.getColumnModel().getColumn(index).setPreferredWidth(0);
        table.getColumnModel().getColumn(index).setResizable(false);
    }

    private void cancelActivation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠ 개통을 취소할 내역을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int activationId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        int userId = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
        int phoneId = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());

        int confirm = JOptionPane.showConfirmDialog(this, "정말로 개통을 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (activationDAO.cancelActivation(activationId, userId, phoneId)) {
                JOptionPane.showMessageDialog(this, "✅ 개통이 취소되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                loadActivationHistory();
            } else {
                JOptionPane.showMessageDialog(this, "❌ 개통 취소에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ActivationHistoryUI::new);
    }
}