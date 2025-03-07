package app.dto;

public class PhoneDTO {
    private int phoneId;
    private String modelName;
    private int carrierId;  // 통신사 ID
    private String carrierName; // 통신사 이름
    private int stockCount;


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

    public String getModelName() {
        return modelName;
    }


    public int getCarrierId() {
        return carrierId;
    }

    public String getCarrierName() {
        return carrierName;
    }


    public int getStockCount() {
        return stockCount;
    }

}