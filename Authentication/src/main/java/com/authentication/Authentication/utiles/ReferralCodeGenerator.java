package com.authentication.Authentication.utiles;

import java.util.Random;

public class ReferralCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;

    public static String generateReferralCode() {
        Random random = new Random();
        StringBuilder referralCode = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            referralCode.append(CHARACTERS.charAt(index));
        }

        return referralCode.toString();
    }

}
