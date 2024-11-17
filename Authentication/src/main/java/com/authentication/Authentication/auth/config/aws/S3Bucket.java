//package com.authentication.Authentication.config.aws;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.services.kms.AWSKMS;
//import com.amazonaws.services.kms.AWSKMSClientBuilder;
//import com.amazonaws.services.s3.AmazonS3;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//
//@Configuration
//public class S3Bucket {
//
//    @Value("${aws.s3.access-key")
//    private String accessKey;
//
//    @Value("${aws.s3.secret-access-key}")
//    private String secretKey;
//
//    @Value("${aws.s3.region}")
//    private String region;
//
//
//    @Bean
//    public AmazonS3 amazonS3() {
//        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials())).withRegion(region).build();
//    }
//
//    @Bean
//    public AWSKMS awskms() {
//        return AWSKMSClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("kms-user-profile"))
//                .withRegion(region).build();
//    }
//
//
//    private AWSCredentials credentials() {
//        return new BasicAWSCredentials(accessKey, secretKey);
//    }
//
//}
