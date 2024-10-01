//package com.authentication.Authentication.utiles;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Service;
//import org.springframework.util.SerializationUtils;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//import java.util.Arrays;
//import java.util.Base64;
//
//@Service
//@RequiredArgsConstructor
//public class AESUtil implements InitializingBean {
//
//    private final DataKeyProvider dataKeyProvider;
//    Key key;
//
//    private final String TRANSFORMATION = "AES";
//
//
//    // Encrypt method
//    public String encrypt(String data) throws Exception {
//        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] encryptedBytes = SerializationUtils.serialize(data);
//        assert encryptedBytes != null;
//        return Base64.getEncoder().encodeToString(cipher.doFinal(encryptedBytes));  // Encode to Base64 string
//    }
//
//    // Decrypt method
//    public String decrypt(String encryptedData) throws Exception {
//        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
//
//        return Arrays.toString(SerializationUtils.clone(decryptedBytes));
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        String ALGORITHM = "AES";
//        key = new SecretKeySpec(dataKeyProvider.generateDataKey(), ALGORITHM);
//    }
//}
