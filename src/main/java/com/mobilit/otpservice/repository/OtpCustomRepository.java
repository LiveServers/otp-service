package com.mobilit.otpservice.repository;

import com.mobilit.otpservice.model.OtpModel;

import java.time.LocalDateTime;
import java.util.List;

public interface OtpCustomRepository {
    List<OtpModel> findByOtpKeyAndOtpAndExpirationDateAndUsedStatus(String otpKey, String otp);
    OtpModel updateUsedStatusToTrueIfQueryResolves(String otpKey, String otp);
}
