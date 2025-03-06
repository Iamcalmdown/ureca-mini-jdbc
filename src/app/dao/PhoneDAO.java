package app.dao;

import app.common.DBManager;
import app.dto.PhoneDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAO {

    // (1) 같은 통신사의 기기 조회 (기기변경)
    public List<PhoneDTO> getPhonesByCarrier(int carrierId) {
        return getPhones("WHERE p.carrier_id = ?", carrierId);
    }

    // (2) 다른 통신사의 기기 조회 (번호이동)
    public List<PhoneDTO> getPhonesByDifferentCarrier(int carrierId) {
        return getPhones("WHERE p.carrier_id <> ?", carrierId);
    }

    // (3) 공통 조회 메서드 (carrierName 포함)
    private List<PhoneDTO> getPhones(String condition, int carrierId) {
        List<PhoneDTO> phones = new ArrayList<>();
        String sql = "SELECT p.phone_id, p.model_name, p.carrier_id, c.carrier_name, p.stock_count " +
                "FROM phone p JOIN carrier c ON p.carrier_id = c.carrier_id " + condition;

        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, carrierId);
            ResultSet resultSet = preparedStatement.executeQuery();

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

    // (4) 개통 시 재고 감소
    public boolean decreaseStock(int phoneId) {
        String sql = "UPDATE phone SET stock_count = stock_count - 1 WHERE phone_id = ? AND stock_count > 0";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, phoneId);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    // (6) 📌 선택한 휴대폰의 통신사 ID 조회
    public int getCarrierIdByPhoneId(int phoneId) {
        String sql = "SELECT carrier_id FROM phone WHERE phone_id = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, phoneId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("carrier_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 조회 실패 시 -1 반환
    }
}