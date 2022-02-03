package com.mobilit.otpservice.service;

import com.mobilit.otpservice.model.OtpDto;
import com.mobilit.otpservice.model.OtpModel;
import com.mobilit.otpservice.repository.OtpRepository;
import com.mobilit.otpservice.response.OtpHeaders;
import com.mobilit.otpservice.response.OtpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClients;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@Service
public class OtpService {
    @Autowired
    OtpRepository otpRepository;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "otp-db");

    public OtpResponse generateOtp(OtpDto dto) {
        try {
            LocalDateTime creationDate = LocalDateTime.now();
            LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(dto.getExpiresIn());
            if (dto.getOtpType().equals("Numeric")) {
                String otpValue = generateNumericRandomOtp(dto.getOtpLength());
                OtpModel model = otpRepository.save(new OtpModel(dto.getOtpKey(), dto.getOtpType(), otpValue, dto.getOtpLength(), creationDate, expirationDate, dto.getExpiresIn()));
                return new OtpResponse(new OtpHeaders("Success", 200, "", LocalDateTime.now()), model.getOtp());
            } else {
                String otpValue = generateAlphanumericRandomOtp(dto.getOtpLength());
                OtpModel model = otpRepository.save(new OtpModel(dto.getOtpKey(), dto.getOtpType(), otpValue, dto.getOtpLength(), creationDate, expirationDate, dto.getExpiresIn()));
                return new OtpResponse(new OtpHeaders("Success", 200, "", LocalDateTime.now()),model.getOtp() );
            }
        } catch (Exception e) {
            return new OtpResponse(new OtpHeaders("The request failed", 200, e.getMessage(), LocalDateTime.now()), null);
        }
    }

    public OtpResponse validateOtp(OtpDto dto) {
        try {
            List<OtpModel> response = otpRepository.findByOtpKeyAndOtpAndExpirationDateAndUsedStatus(dto.getOtpKey(),dto.getOtp());

            if(response.isEmpty()){
                throw new Exception("No result found");
            }else{
                OtpModel model = otpRepository.updateUsedStatusToTrueIfQueryResolves(dto.getOtpKey(),dto.getOtp());
                return new OtpResponse(new OtpHeaders("Successfully verified otp", 200, "", LocalDateTime.now()), model);
            }
        } catch (Exception e) {
            return new OtpResponse(new OtpHeaders("Request Failed", 400, e.getMessage(), LocalDateTime.now()), null);
        }
    }

    public String generateNumericRandomOtp(int otpLength) {
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            builder.append(rand.nextInt(otpLength));
        }
        return builder.toString();
    }

    public String generateAlphanumericRandomOtp(int otpLength) {
        StringBuilder builder = new StringBuilder();
        while (otpLength-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
