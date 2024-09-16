package com.authentication.Authentication.services;

import com.authentication.Authentication.dto.user.UpdateUserDto;
import com.authentication.Authentication.dto.user.UpdateUserDto;
import com.authentication.Authentication.entities.UserInfoEntity;
import com.authentication.Authentication.repo.UserInfoRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfoRepo userInfoRepo;

    @Transactional
    public boolean updateUser(int id, @Valid UpdateUserDto user){
        UserInfoEntity entity = UserInfoEntity.builder()
                .userName(user.getUsername())
                .phone(user.getPhoneNumber())
                .build();
      int dd = userInfoRepo.updateUser(id, user.getUsername(), user.getPhoneNumber());
        System.out.println("updated user " + id + " value: " + dd);
        return dd == 1;
    }

}
