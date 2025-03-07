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
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        // ✅ 테이블 컬럼명 정의 (숨김 컬럼 포함)
        String[] columnNames = {"개통 ID", "사용자 ID", "휴대폰 ID", "이름", "전화번호", "기종", "통신사", "개통 날짜"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ✅ 개통 취소 버튼
        cancelActivationButton = new JButton("개통 취소");
        cancelActivationButton.addActionListener(e -> cancelActivation());
        add(cancelActivationButton, BorderLayout.SOUTH);

        loadActivationHistory(); // 개통 내역 불러오기

        setVisible(true);
    }

    // 📌 개통 내역 불러오기 (컬럼 정리)
    private void loadActivationHistory() {
        tableModel.setRowCount(0); // 기존 데이터 삭제
        List<ActivationDTO> activations = activationDAO.getActivationHistory();

        for (ActivationDTO activation : activations) {
            tableModel.addRow(new Object[]{
                    activation.getActivationId(),   // ✅ 개통 ID (숨김)
                    activation.getUserId(),         // ✅ 사용자 ID (숨김)
                    activation.getPhoneId(),        // ✅ 휴대폰 ID (숨김)
                    activation.getUserName(),       // ✅ 이름 (표시)
                    activation.getPhoneNumber(),    // ✅ 전화번호 (표시)
                    activation.getModelName(),      // ✅ 기종 (표시)
                    activation.getPreviousCarrier() + " → " + activation.getNewCarrier(), // ✅ 통신사 (표시)
                    activation.getActivationDate()  // ✅ 개통 날짜 (표시)
            });
        }

        // ✅ 컬럼 숨기기 (반복 호출에도 유지되도록 개선)
        hideColumn(0); // 개통 ID 숨기기
        hideColumn(1); // 사용자 ID 숨기기
        hideColumn(2); // 휴대폰 ID 숨기기
    }

    // 📌 특정 컬럼 숨기기 (JTable에서 특정 컬럼을 보이지 않도록 설정)
    private void hideColumn(int index) {
        table.getColumnModel().getColumn(index).setMinWidth(0);
        table.getColumnModel().getColumn(index).setMaxWidth(0);
        table.getColumnModel().getColumn(index).setPreferredWidth(0);
        table.getColumnModel().getColumn(index).setResizable(false);
    }

    // 📌 개통 취소 버튼 기능 (UI 갱신 개선)
    private void cancelActivation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠ 개통을 취소할 내역을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ✅ 숨겨진 ID 가져오기
        int activationId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()); // 개통 ID
        int userId = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString()); // 사용자 ID
        int phoneId = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString()); // 휴대폰 ID

        int confirm = JOptionPane.showConfirmDialog(this, "정말로 개통을 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (activationDAO.cancelActivation(activationId, userId, phoneId)) {
                JOptionPane.showMessageDialog(this, "✅ 개통이 취소되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                loadActivationHistory(); // ✅ UI에서 테이블 갱신
            } else {
                JOptionPane.showMessageDialog(this, "❌ 개통 취소에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new ActivationHistoryUI();
    }
}