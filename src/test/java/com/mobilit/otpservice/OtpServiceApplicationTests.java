package com.mobilit.otpservice;

import com.mobilit.otpservice.model.OtpDto;
import com.mobilit.otpservice.model.OtpModel;
import com.mobilit.otpservice.repository.OtpRepository;
import com.mobilit.otpservice.response.OtpResponse;
import com.mobilit.otpservice.service.OtpService;
import com.mongodb.client.MongoClients;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
class OtpServiceApplicationTests {
    MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "otp-db");
    @Autowired
    private OtpService otpService;

    @MockBean
    private OtpRepository otpRepository;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        mongoOps.dropCollection(OtpModel.class);
    }
    @Test
    public void generateOtp() {
        // given
        OtpModel firstInsert = new OtpModel("123","Numeric","1234",4,LocalDateTime.now(),LocalDateTime.now(),60);
        Mockito.when(otpRepository.save(any(OtpModel.class))).thenReturn(firstInsert);
        OtpModel firstResponse = otpRepository.save(firstInsert);

        Mockito.when(otpRepository.findById(firstResponse.getId())).thenReturn(Optional.of(firstInsert));
        Optional<OtpModel> found = otpRepository.findById(firstResponse.getId());

        assertNotNull(found.get());
        assertEquals(firstInsert, found.get());
    }


    @Test
    public void testShouldPassIfDateHasNotExpired(){
        // insert first
        OtpModel firstInsert = new OtpModel("123","Numeric","1234",4,LocalDateTime.now(),LocalDateTime.now().plusSeconds(10800),60);
        Mockito.when(otpRepository.save(any(OtpModel.class))).thenReturn(firstInsert);
        OtpModel firstResponse = otpRepository.save(firstInsert);

        //Returned List
        List<OtpModel> returnedResponse = Arrays.asList(firstResponse);

        Mockito.when(otpRepository.findByOtpKeyAndOtpAndExpirationDateAndUsedStatus(firstResponse.getOtpKey(),firstResponse.getOtp())).thenReturn(returnedResponse);

        List<OtpModel> model = otpRepository.findByOtpKeyAndOtpAndExpirationDateAndUsedStatus(firstResponse.getOtpKey(),firstResponse.getOtp());

        //assertion
        assertEquals(model.get(0),firstResponse);

        // lets now call the update
        firstInsert.setUsed(true);
        Mockito.when(otpRepository.updateUsedStatusToTrueIfQueryResolves(firstResponse.getOtpKey(),firstResponse.getOtp())).thenReturn(firstInsert);
        OtpModel updateResponse = otpRepository.updateUsedStatusToTrueIfQueryResolves(firstResponse.getOtpKey(),firstResponse.getOtp());

        assertEquals(updateResponse.getUsed(),true);

    }
    @Test
    public void shouldFailIfOtpKeyIsNotEqual(){
        // pass different data
        OtpModel firstInsert = new OtpModel("1234","Numeric","1234",4,LocalDateTime.now(),LocalDateTime.now().plusSeconds(10800),10800);
        OtpDto dto = new OtpDto("123","Numeric","1234",4,LocalDateTime.now(),LocalDateTime.now().plusSeconds(10800),10800);

        //Returned List
        List<OtpModel> returnedResponse = Arrays.asList(firstInsert);

        Mockito.when(otpRepository.findByOtpKeyAndOtpAndExpirationDateAndUsedStatus(firstInsert.getOtpKey(),firstInsert.getOtp())).thenReturn(returnedResponse);

        OtpResponse res = otpService.validateOtp(dto);
        assertEquals(res.getHeaders().getStatusCode(),400);
    }

}
