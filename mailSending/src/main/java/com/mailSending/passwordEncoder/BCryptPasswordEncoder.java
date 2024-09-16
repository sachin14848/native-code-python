package com.mailSending.passwordEncoder;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {

    private final int strength;

    public BCryptPasswordEncoder() {
        this(10);  // Default strength
    }

    public BCryptPasswordEncoder(int strength) {
        this.strength = strength;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(strength));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }
}

