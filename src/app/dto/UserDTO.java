package app.dto;

public class UserDTO {
    private String name;
    private String phoneNumber;
    private int carrierId; // 사용자의 기존 통신사 ID

    public UserDTO() {
    }

    public UserDTO(String name, String phoneNumber, int carrierId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.carrierId = carrierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }
}