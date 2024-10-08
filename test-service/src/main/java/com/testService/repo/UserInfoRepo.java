package com.testService.repo;

import com.testService.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserName(String username);
    Optional<UserInfo> findByEmailId(String emailId);

}
