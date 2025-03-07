package app.dto;

import java.sql.Timestamp;

public class ActivationDTO {
    private int activationId; // 기본키
    private int userId; // 개통한 사용자 식별
    private String userName; // 사용자 정보 조회
    private String phoneNumber;
    private int phoneId; // // 개통한 휴대폰 정보 저장
    private String modelName;
    private String previousCarrier; // 통신사 정보 저장
    private String newCarrier;
    private Timestamp activationDate; // 날짜 저장

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