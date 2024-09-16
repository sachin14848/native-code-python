package com.mailSending.controller;

import com.mailSending.dto.OtpRequest;
import com.mailSending.services.MailProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {


    @Autowired
    private MailProducer mailProducer;


    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json"  , produces = "application/json")
    public ResponseEntity<?> sendMail(@RequestBody OtpRequest otpRequest) {
        try {
            String token = mailProducer.generateToken(otpRequest.getEmailTo()); // Assuming generateOtp method is defined in AuthServices class
            return mailProducer.sendMessage(otpRequest, token);

        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Failed To send message : " + e.getMessage());
        }
    }

}