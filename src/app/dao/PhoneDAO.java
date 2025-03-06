package app.dao;

import app.common.DBManager;
import app.dto.PhoneDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAO {

    // 같은 통신사의 기기 조회 (기기변경)  다른 통신사의 기기 조회 (번호이동)

    public List<PhoneDTO> getPhones(int carrierId, boolean isSameCarrier) {
        String condition = isSameCarrier ? "WHERE p.carrier_id = ?" : "WHERE p.carrier_id <> ?";
        return fetchPhoneList(condition, carrierId);
    }

    // (3) 공통 조회 메서드 (carrierName 포함)
    private List<PhoneDTO> fetchPhoneList(String condition, int carrierId) {
        List<PhoneDTO> phones = new ArrayList<>();
        String sql = "SELECT p.phone_id, p.model_name, p.carrier_id, c.carrier_name, p.stock_count " +
                "FROM phone p JOIN carrier c ON p.carrier_id = c.carrier_id " + condition;

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, carrierId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                phones.add(new PhoneDTO(
                        rs.getInt("phone_id"),
                        rs.getString("model_name"),
                        rs.getInt("carrier_id"),
                        rs.getString("carrier_name"),
                        rs.getInt("stock_count")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phones;
    }

    // (4) 개통 시 재고 감소
    public int updateStock(int phoneId, int change) {
        String sql = "UPDATE phone SET stock_count = stock_count + ? WHERE phone_id = ? AND stock_count + ? >= 0";
        String getStockSql = "SELECT stock_count FROM phone WHERE phone_id = ?"; // ✅ 변경된 재고 조회 추가

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, change);
            pstmt.setInt(2, phoneId);
            pstmt.setInt(3, change);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // ✅ 업데이트 성공했으면 변경된 재고 반환
                try (PreparedStatement stockPstmt = con.prepareStatement(getStockSql)) {
                    stockPstmt.setInt(1, phoneId);
                    ResultSet rs = stockPstmt.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("stock_count"); // 📌 정확한 재고 반환
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 실패 시 -1 반환
    }

    // (5) 개통 취소 시 재고 증가
    public boolean increaseStock(int phoneId) {
        String sql = "UPDATE phone SET stock_count = stock_count + 1 WHERE phone_id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, phoneId);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getPhoneIdByModel(String modelName) {
        String sql = "SELECT phone_id FROM phone WHERE model_name = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, modelName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("phone_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 조회 실패 시 -1 반환
    }
}