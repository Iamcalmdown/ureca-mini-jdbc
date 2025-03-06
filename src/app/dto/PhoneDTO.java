package app.dto;

public class PhoneDTO {
    private int id;
    private String model;
    private int carrierId;

    public PhoneDTO(int id, String model, int carrierId) {
        this.id = id;
        this.model = model;
        this.carrierId = carrierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }
}