package app.dto;

import java.sql.Timestamp;
import java.util.Date;

public class ActivationDTO {
    private int activationId;
    private int userId;
    private int phoneId;
    private int previousCarrierId; // 기존 통신사
    private int newCarrierId; // 변경된 통신사
    private Date activationDate;

    public ActivationDTO(int activationId, String name, String phoneNumber, String modelName, String carrierName, Timestamp activationDate) {
    }

    public ActivationDTO(int userId, int phoneId, int previousCarrierId, int newCarrierId) {
        this.userId = userId;
        this.phoneId = phoneId;
        this.previousCarrierId = previousCarrierId;
        this.newCarrierId = newCarrierId;
    }

    public int getActivationId() {
        return activationId;
    }

    public void setActivationId(int activationId) {
        this.activationId = activationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public int getPreviousCarrierId() {
        return previousCarrierId;
    }

    public void setPreviousCarrierId(int previousCarrierId) {
        this.previousCarrierId = previousCarrierId;
    }

    public int getNewCarrierId() {
        return newCarrierId;
    }

    public void setNewCarrierId(int newCarrierId) {
        this.newCarrierId = newCarrierId;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }
}