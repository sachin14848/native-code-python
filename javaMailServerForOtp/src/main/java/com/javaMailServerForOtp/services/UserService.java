package com.javaMailServerForOtp.services;

import com.javaMailServerForOtp.dto.user.UpdateUserDto;
import com.javaMailServerForOtp.dto.user.User;
import com.javaMailServerForOtp.entities.UserInfoEntity;
import com.javaMailServerForOtp.repo.UserInfoRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfoRepo userInfoRepo;

    @Transactional
    public boolean updateUser(int id,UpdateUserDto user){
        UserInfoEntity entity = UserInfoEntity.builder()
                .userName(user.getUsername())
                .phone(user.getPhoneNumber())
                .build();
      int dd = userInfoRepo.updateUser(id, user.getUsername(), user.getPhoneNumber());
        System.out.println("updated user " + id + " value: " + dd);
        return dd == 1;
    }

}
