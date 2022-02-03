package com.mobilit.otpservice.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OtpHeaders {
    private String customerMessage;
    private int statusCode;
    private String errorMessage;
    private LocalDateTime responseTime;

    public OtpHeaders(String customerMessage, int statusCode, String errorMessage, LocalDateTime responseTime) {
        this.customerMessage = customerMessage;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.responseTime = responseTime;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
