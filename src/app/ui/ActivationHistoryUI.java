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
                    activation.getActivationId(), // ✅ 개통 ID (숨김 처리)
                    activation.getUserId(),       // ✅ 사용자 ID (숨김 처리)
                    activation.getPhoneId(),      // ✅ 휴대폰 ID (숨김 처리)
                    activation.getUserName(),     // ✅ 이름 (표시)
                    activation.getPhoneNumber(),  // ✅ 전화번호 (표시)
                    activation.getModelName(),    // ✅ 기종 (표시)
                    activation.getPreviousCarrier() + " → " + activation.getNewCarrier(), // ✅ 통신사 (표시)
                    activation.getActivationDate() // ✅ 개통 날짜 (표시)
            });
        }

        // 🔥 ID 컬럼을 정확하게 숨기기 (앞에서부터 3개 숨김)
        table.removeColumn(table.getColumnModel().getColumn(0)); // 개통 ID 숨기기
        table.removeColumn(table.getColumnModel().getColumn(0)); // 사용자 ID 숨기기
        table.removeColumn(table.getColumnModel().getColumn(0)); // 휴대폰 ID 숨기기
    }

    // 📌 특정 컬럼 숨기기 (JTable에서 특정 컬럼을 보이지 않도록 설정)
    private void hideColumn(int index) {
        table.getColumnModel().getColumn(index).setMinWidth(0);
        table.getColumnModel().getColumn(index).setMaxWidth(0);
        table.getColumnModel().getColumn(index).setWidth(0);
    }

    // 📌 개통 취소 버튼 기능 수정
    private void cancelActivation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "취소할 개통 내역을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ✅ 숨겨진 ID 가져오기 (테이블에 보이지 않지만 내부적으로 저장됨)
        int activationId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()); // 개통 ID
        int userId = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString()); // 사용자 ID
        int phoneId = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString()); // 휴대폰 ID

        int confirm = JOptionPane.showConfirmDialog(this, "정말로 개통을 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (activationDAO.cancelActivation(activationId, userId, phoneId)) {
                JOptionPane.showMessageDialog(this, "개통이 취소되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                loadActivationHistory(); // ✅ 개통 내역 새로고침
            } else {
                JOptionPane.showMessageDialog(this, "개통 취소에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new ActivationHistoryUI();
    }
}