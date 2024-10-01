//package com.authentication.Authentication.utiles;
//
//import com.amazonaws.services.kms.AWSKMS;
//import com.amazonaws.services.kms.model.DecryptRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.nio.ByteBuffer;
//import java.util.Base64;
//
//@Component
//@RequiredArgsConstructor
//public class DataKeyProvider {
//
//    private final AWSKMS awskms;
//
//    public byte[] generateDataKey() {
//        String accessKey = "kjdsfhjksdhdfk8y8734bahfkjsdflkd5435345dfgfdgdfg454tgkgifgiui5u54uignkjnkfdjghiuert45y98j424j342m3n4DFGDFgshfksdf";
//        return awskms.decrypt(new DecryptRequest()
//                .withCiphertextBlob(ByteBuffer.wrap(Base64.getDecoder().decode(accessKey.getBytes())))
//        ).getPlaintext().array();
//    }
//
//}
