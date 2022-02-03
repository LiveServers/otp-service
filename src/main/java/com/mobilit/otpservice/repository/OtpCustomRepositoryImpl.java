package com.mobilit.otpservice.repository;

import com.mobilit.otpservice.model.OtpModel;

import java.time.LocalDateTime;
import java.util.List;

import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class OtpCustomRepositoryImpl implements OtpCustomRepository{
    MongoOperations mongoTemplate = new MongoTemplate(MongoClients.create(), "otp-db");
    @Override
    public List<OtpModel> findByOtpKeyAndOtpAndExpirationDateAndUsedStatus(String otpKey,String otp) {
        Query query = new Query(where("otpKey").is(otpKey).and("otp").is(otp).and("expirationDate").gte(LocalDateTime.now()).and("used").is(false));
        return mongoTemplate.query(OtpModel.class).matching(query).all();
    }

    @Override
    public OtpModel updateUsedStatusToTrueIfQueryResolves(String otpKey, String otp) {
        Query query = new Query(where("otpKey").is(otpKey).and("otp").is(otp));
        Update update = new Update().set("used",true);

        return mongoTemplate.update(OtpModel.class).matching(query).apply(update).withOptions(FindAndModifyOptions.options().returnNew(true)).findAndModifyValue();
    }

}
