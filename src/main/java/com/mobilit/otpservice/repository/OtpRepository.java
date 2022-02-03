package com.mobilit.otpservice.repository;

import com.mobilit.otpservice.model.OtpModel;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OtpRepository extends MongoRepository<OtpModel, String>,OtpCustomRepository { }

