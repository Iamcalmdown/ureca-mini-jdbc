package app.dto;

public class UserDTO {
    private int id;
    private String name;
    private String phoneNumber;
    private int carrierId;

    public UserDTO(int id, String name, String phoneNumber, int carrierId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.carrierId = carrierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
