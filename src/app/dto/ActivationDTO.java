package app.dto;

import java.sql.Timestamp;

public class ActivationDTO {
    private int activationId;
    private int userId; // ✅ user_id 추가
    private String userName;
    private String phoneNumber;
    private int phoneId; // ✅ phone_id 추가
    private String modelName;
    private String previousCarrier;
    private String newCarrier;
    private Timestamp activationDate;

    public ActivationDTO(int activationId, int userId, String userName, String phoneNumber, int phoneId,
                         String modelName, String previousCarrier, String newCarrier, Timestamp activationDate) {
        this.activationId = activationId;
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.phoneId = phoneId;
        this.modelName = modelName;
        this.previousCarrier = previousCarrier;
        this.newCarrier = newCarrier;
        this.activationDate = activationDate;
    }

    public int getActivationId() {
        return activationId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public String getModelName() {
        return modelName;
    }

    public String getPreviousCarrier() {
        return previousCarrier;
    }

    public String getNewCarrier() {
        return newCarrier;
    }

    public Timestamp getActivationDate() {
        return activationDate;
    }
}