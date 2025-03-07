package app.dao;

import app.common.DBManager;
import app.dto.PhoneDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAO {

    // 기기변경, 번호이동 선택
    public List<PhoneDTO> getPhones(int carrierId, boolean isSameCarrier) {
        String condition = isSameCarrier ? "WHERE p.carrier_id = ?" : "WHERE p.carrier_id <> ?";
        return fetchPhoneList(condition, carrierId);
    }

    private List<PhoneDTO> fetchPhoneList(String condition, int carrierId) {
        List<PhoneDTO> phones = new ArrayList<>();
        String sql = "SELECT p.phone_id, p.model_name, p.carrier_id, c.carrier_name, p.stock_count " +
                "FROM phone p JOIN carrier c ON p.carrier_id = c.carrier_id " + condition;

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, carrierId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                phones.add(new PhoneDTO(
                        resultSet.getInt("phone_id"),
                        resultSet.getString("model_name"),
                        resultSet.getInt("carrier_id"),
                        resultSet.getString("carrier_name"),
                        resultSet.getInt("stock_count")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phones;
    }

    // 개통 시 재고 감소
    public int updateStock(int phoneId, int change) {
        String sql = "UPDATE phone SET stock_count = stock_count + ? WHERE phone_id = ? AND stock_count + ? >= 0";
        String getStockSql = "SELECT stock_count FROM phone WHERE phone_id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, change);
            pstmt.setInt(2, phoneId);
            pstmt.setInt(3, change);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // 수정완료되면 바로 조회쿼리 동작
                try (PreparedStatement stockPstmt = connection.prepareStatement(getStockSql)) {
                    stockPstmt.setInt(1, phoneId);
                    ResultSet resultSet = stockPstmt.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt("stock_count"); // 재고 반환
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 실패 시 -1 반환
    }

    // 개통 취소 시 재고 증가
    public boolean increaseStock(int phoneId) {
        String sql = "UPDATE phone SET stock_count = stock_count + 1 WHERE phone_id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, phoneId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}