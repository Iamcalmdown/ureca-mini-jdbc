package app.dto;

public class UserDTO {
    private int userId; // ✅ 추가
    private String name;
    private String phoneNumber;
    private int carrierId;

    // ✅ 기존 사용자 조회 시 사용 (userId 포함)
    public UserDTO(int userId, String name, String phoneNumber, int carrierId) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.carrierId = carrierId;
    }

    // ✅ 새로운 사용자 등록 시 사용 (userId 없이)
    public UserDTO(String name, String phoneNumber, int carrierId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.carrierId = carrierId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getCarrierId() {
        return carrierId;
    }
}