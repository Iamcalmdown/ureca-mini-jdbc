package app.dto;

public class PhoneDTO {
    private int phoneId;
    private String modelName;
    private int carrierId;  // 통신사 ID
    private String carrierName; // 통신사 이름
    private int stockCount;

    public PhoneDTO() {
    }

    public PhoneDTO(int phoneId, String modelName, int carrierId, String carrierName, int stockCount) {
        this.phoneId = phoneId;
        this.modelName = modelName;
        this.carrierId = carrierId;
        this.carrierName = carrierName;
        this.stockCount = stockCount;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }
}