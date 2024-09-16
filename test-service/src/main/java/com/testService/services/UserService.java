package com.testService.services;

import com.testService.entity.UserInfo;
import com.testService.repo.UserInfoRepo;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserInfoRepo userInfoRepo;

    public UserInfo getUserDetails(String username, Authentication auth) {
        // Logic to fetch user details from a database or API
        return userInfoRepo.findByUserName(username).orElseThrow(() -> new NotFoundException("User not found"));

    }

    public UserInfo saveUserDetails(UserInfo userInfo) {
        // Logic to save user details to a database or API
        return userInfoRepo.save(userInfo);
    }

}
