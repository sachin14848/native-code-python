package com.authentication.Authentication.dto.user;

import com.authentication.Authentication.entities.UserInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long userId;
    private String username;
    private String email;
    private String phoneNo;
    private String role;

    public User(UserInfoEntity userInfoEntity) {
        this.userId = userInfoEntity.getId();
        this.username = userInfoEntity.getUserName();
        this.email = userInfoEntity.getEmailId();
        this.phoneNo = userInfoEntity.getPhone();
        this.role = userInfoEntity.getRoles();
    }
}
