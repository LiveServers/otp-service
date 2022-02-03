package com.mobilit.otpservice.model;


import java.time.LocalDateTime;

public class OtpDto {

    private String id;
    private String otpKey;
    private String otpType;
    private String otp;
    private int otpLength;
    private int expiresIn;
    private Boolean used = false;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    public OtpDto(String otpKey, String otpType, String otp, int otpLength, LocalDateTime creationDate, LocalDateTime expirationDate, int expiresIn) {
        this.otpKey = otpKey;
        this.otpType = otpType;
        this.otp = otp;
        this.otpLength = otpLength;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.expiresIn = expiresIn;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getOtpLength() {
        return otpLength;
    }

    public void setOtpLength(int otpLength) {
        this.otpLength = otpLength;
    }

    public String getOtpType() {
        return otpType;
    }

    public void setOtpType(String otpType) {
        this.otpType = otpType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getOtpKey() {
        return otpKey;
    }

    public void setOtpKey(String otpKey) {
        this.otpKey = otpKey;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "OtpDto{" +
                "id='" + id + '\'' +
                ", otpKey='" + otpKey + '\'' +
                ", otpType='" + otpType + '\'' +
                ", otp='" + otp + '\'' +
                ", otpLength=" + otpLength +
                ", expiresIn=" + expiresIn +
                ", used=" + used +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
