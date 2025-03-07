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

        // ✅ UI 제목 추가
        JLabel titleLabel = UIComponents.createLabel("📜 개통 내역");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // ✅ 테이블 컬럼명 정의
        String[] columnNames = {"개통 ID", "사용자 ID", "휴대폰 ID", "이름", "전화번호", "기종", "통신사", "개통 날짜"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = UIComponents.createStyledTable(columnNames, new String[0][8]); // 🎨 스타일 적용된 테이블
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ✅ 개통 취소 버튼
        cancelActivationButton = UIComponents.createStyledButton("개통 취소");
        cancelActivationButton.addActionListener(e -> cancelActivation());
        add(cancelActivationButton, BorderLayout.SOUTH);

        loadActivationHistory(); // ✅ 개통 내역 불러오기
        setVisible(true);
    }

    // 📌 개통 내역 불러오기
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

        // ✅ 컬럼 숨기기 (개통 ID, 사용자 ID, 휴대폰 ID)
        hideColumn(0);
        hideColumn(1);
        hideColumn(2);
    }

    // 📌 특정 컬럼 숨기기
    private void hideColumn(int index) {
        table.getColumnModel().getColumn(index).setMinWidth(0);
        table.getColumnModel().getColumn(index).setMaxWidth(0);
        table.getColumnModel().getColumn(index).setPreferredWidth(0);
        table.getColumnModel().getColumn(index).setResizable(false);
    }

    // 📌 개통 취소 기능
    private void cancelActivation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠ 개통을 취소할 내역을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ✅ 숨겨진 ID 가져오기
        int activationId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        int userId = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
        int phoneId = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());

        int confirm = JOptionPane.showConfirmDialog(this, "정말로 개통을 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (activationDAO.cancelActivation(activationId, userId, phoneId)) {
                JOptionPane.showMessageDialog(this, "✅ 개통이 취소되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                loadActivationHistory(); // ✅ UI 갱신
            } else {
                JOptionPane.showMessageDialog(this, "❌ 개통 취소에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new ActivationHistoryUI();
    }
}