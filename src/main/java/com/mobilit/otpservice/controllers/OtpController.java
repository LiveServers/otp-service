package com.mobilit.otpservice.controllers;

import com.mobilit.otpservice.model.OtpDto;
import com.mobilit.otpservice.response.OtpResponse;
import com.mobilit.otpservice.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/otp")
@CrossOrigin
public class OtpController {
    @Autowired
    OtpService otpService;

    @PostMapping("/generate-otp")
    public OtpResponse generateOtp(@RequestBody OtpDto dto) {
        return otpService.generateOtp(dto);
    }

    @GetMapping("/validate-otp")
    public OtpResponse validateOtp(@RequestBody OtpDto dto) {
        return otpService.validateOtp(dto);
    }

}