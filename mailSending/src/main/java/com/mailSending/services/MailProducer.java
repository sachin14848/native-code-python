package com.mailSending.services;

import com.mailSending.dto.CustomErrorResponse;
import com.mailSending.dto.OtpRequest;
import com.mailSending.dto.OtpResponse;
import com.mailSending.entities.Otp;
import com.mailSending.entities.UserInfoEntity;
import com.mailSending.passwordEncoder.PasswordEncoder;
import com.mailSending.repo.OtpRepo;
import com.mailSending.repo.UserInfoRepo;
import com.mailSending.errorHandler.EmailSendingException;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

//@RequiredArgsConstructor
@Service
public class MailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private OtpRepo otpRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;
    private static final String HMAC_ALGO = "HmacSHA256";
    private static final String SECRET_KEY = "your-secret-key";


    public MailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.err.println("Message could not be routed: " + returned.getMessage());
                throw new EmailSendingException("Failed to send email: " + returned.getMessage()); // Propagate error to the calling method
            }
        });
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack) {
                    System.err.println("Message not acknowledged by broker: " + cause);
                    throw new EmailSendingException("Message not acknowledged by broker: " + cause);
                }
            }
        });
    }


    public ResponseEntity<?> sendMessage(OtpRequest otpRequest, String token) {
        // Prepare the email message
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(otpRequest.getEmailTo());
            mailMessage.setSubject("ProGrowW Otp Verification");
            String otp = generateRandom6DigitNumber();
            String html = "This is a test email" + "Your OTP is: " + otp;
            mailMessage.setText(html);
            updateOtp(otp, otpRequest.getEmailTo());
            rabbitTemplate.convertAndSend("main_queue", mailMessage);
            OtpResponse response = new  OtpResponse();
            response.setToken(token);
            response.setCode(HttpStatus.OK.value());
            response.setMessage("OTP sent successfully ye.");
            response.setStatus("success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse();
            errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setStatus("failed");
            errorResponse.setMessage("Failed to send OTP. Please try again later.");
            errorResponse.setError(e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    public ResponseEntity<?> processMail(SimpleMailMessage mailMessage) {
        try {
            mailSender.send(mailMessage);
            return ResponseEntity.ok("Email sent successfully processMail by RabbitMQ");
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            throw new EmailSendingException("Failed to send email in processMail : " + e.getMessage(), e);
        }
    }

    public String generateRandom6DigitNumber() {
        int number = (int) (Math.random() * 900000) + 100000; // Generate a random number between 100000 and 999999
        return String.format("%06d", number); // Format the number as a 6-digit string with leading zeros
    }


    private void updateOtp(String otp, String emailTo) {
        UserInfoEntity userInfoEntity = userInfoRepo.findByEmailId(emailTo).orElse(null);
        if (userInfoEntity != null) {
            Otp otpEntity = new Otp();
            otpEntity.setOtp(otp);
            otpEntity.setId(userInfoEntity.getId());
            otpEntity.setUsed(false);
            otpEntity.setExpiryTime(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES))); // Set OTP expiration time to 5 minutes from
            otpRepo.save(otpEntity);
        }
    }

    public String generateToken(String username) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGO);
            final String NEW_SECRET_KEY = SECRET_KEY + username; // replace with your secret key
            SecretKeySpec secretKeySpec = new SecretKeySpec(NEW_SECRET_KEY.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
            mac.init(secretKeySpec);
            final String token = UUID.randomUUID().toString();
            updateUserInfo(token, username);
            return token;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed Sending Mail");
        }
    }

    private void updateUserInfo(String password, String emailTo) {

        UserInfoEntity userInfoEntity = userInfoRepo.findByEmailId(emailTo).orElse(null);
        if (userInfoEntity == null) {
            userInfoEntity = new UserInfoEntity();
            userInfoEntity.setEmailId(emailTo);
            userInfoEntity.setPassword(passwordEncoder.encode(password));
            userInfoEntity.setRoles("ROLE_USER");
            userInfoEntity.setOtpExpirationTime(Date.from(Instant.now().plus(3, ChronoUnit.MINUTES))); // Set OTP expiration time to 5 minutes from
        } else {
            userInfoEntity.setOtpExpirationTime(Date.from(Instant.now().plus(2, ChronoUnit.MINUTES))); // Set OTP expiration time to 5 minutes from
            userInfoEntity.setPassword(passwordEncoder.encode(password)); // Encode the password using the configured encoder
        }
        userInfoRepo.save(userInfoEntity);// Encode the password using the configured encoder
    }

}