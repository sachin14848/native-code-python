//package com.authentication.Authentication.entities;
//
//import com.authentication.Authentication.utiles.AESUtil;
//import jakarta.persistence.AttributeConverter;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//public class AttributesConvertor implements AttributeConverter<String, String> {
//
//    private final AESUtil aesUtil;
//
//    @Override
//    public String convertToDatabaseColumn(String s) {
//        try {
//            return aesUtil.encrypt(s);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public String convertToEntityAttribute(String s) {
//        try {
//            return aesUtil.decrypt(s);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
