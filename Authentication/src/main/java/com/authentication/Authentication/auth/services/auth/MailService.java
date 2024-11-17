package com.authentication.Authentication.auth.services.auth;

import com.authentication.Authentication.auth.dto.MailDto;
import com.authentication.Authentication.auth.entities.auth.UserInfoEntity;
import com.authentication.Authentication.auth.entities.auth.otp.Otp;
import com.authentication.Authentication.auth.repo.auth.UserInfoRepo;
import com.authentication.Authentication.auth.repo.auth.OtpRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@RequiredArgsConstructor
@Service
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepo userInfoRepo;

    private final OtpRepo otpRepo;

    private static final String HMAC_ALGO = "HmacSHA256";
    private static final String SECRET_KEY = "your-secret-key";

    //    @Async
    public String sendEmail(@Valid MailDto mailDto, String token) {
        try {
            log.info("Sending email to: {}", mailDto.getEmailTo());
            MimeMessage message = mailSender.createMimeMessage();
            String otp = mailSenderHelper(message, mailDto.getEmailTo());
            updateOtp(otp, mailDto.getEmailTo());
            mailSender.send(message);
            return token;
        } catch (MessagingException e) {
            return null;
        }
    }

    private String mailSenderHelper(MimeMessage message, String sendTo) throws MessagingException {
        MimeMessageHelper mimeMessage = new MimeMessageHelper(message, true);
        mimeMessage.setTo(sendTo);
        mimeMessage.setSubject("Sachin Mishra");
        String otp = generateRandom6DigitNumber();
        String html = "<h1>Hello World!</h1><p>This is a test email.</p>" + "<p>Your OTP is: " + otp + "</p>" + "<p>";
        mimeMessage.setText(html, true);
        return otp;
    }

    public String generateRandom6DigitNumber() {
        int number = (int) (Math.random() * 900000) + 100000; // Generate a random number between 100000 and 999999
        return String.format("%06d", number); // Format the number as a 6-digit string with leading zeros
    }

    private void updateUserInfo(String password, String emailTo) {

        UserInfoEntity userInfoEntity = userInfoRepo.findByEmailId(emailTo).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        if (userInfoEntity != null) {
            userInfoEntity.setOtpExpirationTime(Date.from(Instant.now().plus(2, ChronoUnit.MINUTES))); // Set OTP expiration time to 5 minutes from
            userInfoEntity.setPassword(passwordEncoder.encode(password)); // Encode the password using the configured encoder
        }
        assert userInfoEntity != null;
        userInfoRepo.save(userInfoEntity);// Encode the password using the configured encoder
    }

    private void updateOtp(String otp, String emailTo) {
        UserInfoEntity userInfoEntity = userInfoRepo.findByEmailId(emailTo)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
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

    public boolean isValidateOtp(String Otp, String username) {
        try {
            UserInfoEntity userInfoEntity = userInfoRepo.findByEmailId(username).orElseThrow(() -> new RuntimeException("User not found"));
            Otp otpEntity = otpRepo.findByOtpAndId(Otp, userInfoEntity.getId()).orElseThrow(() -> new RuntimeException("Invalid OTP"));
            if (otpEntity != null && otpEntity.getExpiryTime().after(new Date())) {
                otpEntity.setUsed(true);
                otpRepo.delete(otpEntity);
                return true;
            }
        } catch (Exception e) {
            log.error("Error validating OTP", e);
            return false;
        }
        return false;
    }


}
