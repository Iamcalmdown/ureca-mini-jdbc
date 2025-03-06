package app.dto;

import java.sql.Timestamp;

public class ActivationDTO {
    private int activationId;
    private String userName;
    private String phoneNumber;
    private String modelName;
    private String previousCarrier; // 기존 통신사 추가
    private String newCarrier; // 변경된 통신사 추가
    private Timestamp activationDate;

    // 📌 생성자 수정
    public ActivationDTO(int activationId, String userName, String phoneNumber, String modelName,
                         String previousCarrier, String newCarrier, Timestamp activationDate) {
        this.activationId = activationId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.modelName = modelName;
        this.previousCarrier = previousCarrier;
        this.newCarrier = newCarrier;
        this.activationDate = activationDate;
    }

    // 📌 Getter & Setter 추가
    public int getActivationId() {
        return activationId;
    }

    public void setActivationId(int activationId) {
        this.activationId = activationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPreviousCarrier() {
        return previousCarrier;
    }

    public void setPreviousCarrier(String previousCarrier) {
        this.previousCarrier = previousCarrier;
    }

    public String getNewCarrier() {
        return newCarrier;
    }

    public void setNewCarrier(String newCarrier) {
        this.newCarrier = newCarrier;
    }

    public Timestamp getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Timestamp activationDate) {
        this.activationDate = activationDate;
    }
}