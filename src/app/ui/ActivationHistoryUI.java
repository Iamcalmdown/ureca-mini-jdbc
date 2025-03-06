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
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 테이블 초기화
        String[] columnNames = {"ID", "이름", "전화번호", "기종", "통신사", "개통 날짜"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 개통 취소 버튼
        cancelActivationButton = new JButton("개통 취소");
        cancelActivationButton.addActionListener(e -> cancelActivation());
        add(cancelActivationButton, BorderLayout.SOUTH);

        loadActivationHistory(); // 개통 내역 불러오기

        setVisible(true);
    }

    // 개통 내역 불러오기
    // 📌 개통 내역 UI에서 데이터 불러오는 부분 수정
    private void loadActivationHistory() {
        tableModel.setRowCount(0); // 기존 데이터 삭제
        List<ActivationDTO> activations = activationDAO.getActivationHistory();

        for (ActivationDTO activation : activations) {
            tableModel.addRow(new Object[]{
                    activation.getActivationId(),
                    activation.getUserId(),  // ✅ user_id 추가 (hidden column)
                    activation.getUserName(),  // ✅ 사용자 이름
                    activation.getPhoneNumber(), // ✅ 전화번호
                    activation.getPhoneId(),  // ✅ phone_id 추가 (hidden column)
                    activation.getModelName(),
                    activation.getPreviousCarrier() + " → " + activation.getNewCarrier(), // ✅ 기존 통신사 → 변경된 통신사
                    activation.getActivationDate()
            });
        }
    }

    // 📌 개통 취소 버튼 기능 수정
    private void cancelActivation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "취소할 개통 내역을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int activationId = (int) tableModel.getValueAt(selectedRow, 0);
        int userId = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString()); // ✅ user_id 가져오기
        int phoneId = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString()); // ✅ phone_id 가져오기

        int confirm = JOptionPane.showConfirmDialog(this, "정말로 개통을 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            activationDAO.cancelActivation(activationId, userId, phoneId);
            JOptionPane.showMessageDialog(this, "개통이 취소되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            loadActivationHistory(); // 최신 데이터 다시 불러오기
        }
    }

    public static void main(String[] args) {
        new ActivationHistoryUI();
    }
}