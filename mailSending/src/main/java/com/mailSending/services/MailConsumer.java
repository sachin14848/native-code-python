package com.mailSending.services;

import com.mailSending.config.CustomMessageConverter;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailConsumer {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "main_queue")
    public void receiveMessage(Message message, Channel channel) {

        try {
            SimpleMailMessage simpleMailMessage = (SimpleMailMessage) new CustomMessageConverter().fromMessage(message);
            mailSender.send(simpleMailMessage);
            // Process the message
            System.out.println("Received message: " + new String(message.getBody()));
            // Manually acknowledge after successful processing
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (MailException e) {
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (AmqpException e) {
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            // Log any other errors and requeue the message
            System.err.println("Unexpected error AmqpException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


//    String correlationId = message.getMessageProperties().getCorrelationId();
//    String replyToQueue = message.getMessageProperties().getReplyTo();

//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
//    public void consumeMailMessage(String message) {
    // Assuming message is the email content; customize as needed
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo("sachim888777@gmail.com");
//        mailMessage.setSubject("Subject");
//        mailMessage.setText(message);
//
//        try {
//            javaMailSender.send(mailMessage);
//        } catch (MailException e) {
//            // Log the error if the email couldn't be sent
//            System.err.println("Error sending email: " + e.getMessage());
//        }
//    }

//    private String mailSenderHelper(MimeMessage message) throws MessagingException {
//        MimeMessageHelper mimeMessage = new MimeMessageHelper(message, true);
//        mimeMessage.setTo("sachim888777@gmail.com");
//        mimeMessage.setSubject("Sachin Mishra");
//        String otp = generateRandom6DigitNumber();
//        String html = "<h1>Hello World!</h1><p>This is a test email.</p>" + "<p>Your OTP is: " + otp + "</p>" + "<p>";
//        mimeMessage.setText(html, true);
//        return otp;
//    }

//    public String generateRandom6DigitNumber() {
//        int number = (int) (Math.random() * 900000) + 100000; // Generate a random number between 100000 and 999999
//        return String.format("%06d", number); // Format the number as a 6-digit string with leading zeros
//    }

//    private void updateUserInfo(String password, String emailTo) {
//
//        UserInfoEntity userInfoEntity = userInfoRepo.findByEmailId(emailTo).orElse(null);
//        if (userInfoEntity == null) {
//            userInfoEntity = new UserInfoEntity();
//            userInfoEntity.setEmailId(emailTo);
//            userInfoEntity.setPassword(passwordEncoder.encode(password));
//            userInfoEntity.setRoles("ROLE_USER");
//            userInfoEntity.setOtpExpirationTime(Date.from(Instant.now().plus(3, ChronoUnit.MINUTES))); // Set OTP expiration time to 5 minutes from
//        } else {
//            userInfoEntity.setOtpExpirationTime(Date.from(Instant.now().plus(2, ChronoUnit.MINUTES))); // Set OTP expiration time to 5 minutes from
//            userInfoEntity.setPassword(passwordEncoder.encode(password)); // Encode the password using the configured encoder
//        }
//        userInfoRepo.save(userInfoEntity);// Encode the password using the configured encoder
//    }

//    private void updateOtp(String otp, String emailTo) {
//        UserInfoEntity userInfoEntity = userInfoRepo.findByEmailId(emailTo).orElse(null);
//        if (userInfoEntity != null) {
//            Otp otpEntity = new Otp();
//            otpEntity.setOtp(otp);
//            otpEntity.setId(userInfoEntity.getId());
//            otpEntity.setUsed(false);
//            otpEntity.setExpiryTime(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES))); // Set OTP expiration time to 5 minutes from
//            otpRepo.save(otpEntity);
//        }
//    }

//    public String generateToken(String username) {
//        try {
//            Mac mac = Mac.getInstance(HMAC_ALGO);
//            final String NEW_SECRET_KEY = SECRET_KEY + username; // replace with your secret key
//            SecretKeySpec secretKeySpec = new SecretKeySpec(NEW_SECRET_KEY.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
//            mac.init(secretKeySpec);
//            final String token = UUID.randomUUID().toString();
//            updateUserInfo(token, username);
//            return token;
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            throw new RuntimeException("Failed Sending Mail");
//        }
//    }

}


//@RabbitListener(queues = "main_queue")
//public void receiveMessage(Message message, Channel channel) {
//
//    String correlationId = message.getMessageProperties().getCorrelationId();
//    String replyToQueue = message.getMessageProperties().getReplyTo();
//    try {
//        SimpleMailMessage simpleMailMessage = (SimpleMailMessage) new CustomMessageConverter().fromMessage(message);
//
//        mailSender.send(simpleMailMessage);
//        // Process the message
//        System.out.println("Received message: " + new String(message.getBody()));
//        String successMessage = "success";
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setCorrelationId(correlationId);  // Set correlation ID if necessary
//
////          Convert the successMessage (String) to a Message object
//        Message messageSend = new Message(successMessage.getBytes(StandardCharsets.UTF_8), messageProperties);
//        rabbitTemplate.send("", replyToQueue, messageSend, new CorrelationData(correlationId));
//
//        // Manually acknowledge after successful processing
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
////            throw new RuntimeException("Failed to send email: ");
//    } catch (MailException e) {
//        String errorMessage = "Error sending email MailException: " + e.getMessage();
//        try {
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//        } catch (IOException ioException) {
//            rabbitTemplate.convertAndSend("", replyToQueue, "Unexpected error AmqpException: " + ioException.getMessage(), new CorrelationData(correlationId));
//        }
//        rabbitTemplate.convertAndSend("", replyToQueue, errorMessage, new CorrelationData(correlationId));
//        System.err.println("Error sending email: " + e.getMessage());
//    } catch (AmqpException e) {
//        String errorMessage = "Unexpected error AmqpException: " + e.getMessage();
//        try {
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//        } catch (IOException ioException) {
//            rabbitTemplate.convertAndSend("", replyToQueue, "Unexpected error AmqpException: " + ioException.getMessage(), new CorrelationData(correlationId));
//        }
//        rabbitTemplate.convertAndSend("", replyToQueue, errorMessage, new CorrelationData(correlationId));
//        // Log any other errors and requeue the message
//        System.err.println("Unexpected error AmqpException: " + e.getMessage());
//
//    } catch (Exception e) {
//        System.err.println("Unexpected error: " + e.getMessage());
//        String errorMessage = "Unexpected error: " + e.getMessage();
//        try {
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//        } catch (IOException ioException) {
//            rabbitTemplate.convertAndSend("", replyToQueue, "Unexpected error AmqpException: " + ioException.getMessage(), new CorrelationData(correlationId));
//        }
//        rabbitTemplate.convertAndSend("", replyToQueue, errorMessage, new CorrelationData(correlationId));
//    }
//}

