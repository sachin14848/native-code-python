package com.authentication.Authentication.auth.config.security;

import com.authentication.Authentication.auth.repo.auth.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoManagerConfig implements UserDetailsService {

//    private static final Logger log = LoggerFactory.getLogger(UserInfoManagerConfig.class);
    private final UserInfoRepo userInfoRepo;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        log.info("UserManagerConfig:loadUserByUsername called with loadUserByUsername @@@@@@@ emailId : " + emailId);
       try{
           UserInfoConfig userInfoConfig = userInfoRepo
                   .findByEmailId(emailId)
                   .map(UserInfoConfig::new)
                   .orElseThrow(() -> new UsernameNotFoundException("User EmailId : " + emailId + "does not exist!"));
           if (isExpiryOTP(userInfoConfig.getOtpExpirationTime())) {
               throw new UsernameNotFoundException("OTP expired for user : " + emailId);
           }
           return userInfoConfig;
       }catch (BadCredentialsException ex){
           log.error("UserManagerConfig:loadUserByUsername BadCredentialsException occurred for emailId : " + emailId + " and exception : " + ex.getMessage()  );
           throw new BadCredentialsException("Invalid credentials for user : " + emailId);
       }


    }

    private boolean isExpiryOTP(Date data) {
        return data.before(new Date());
    }
}