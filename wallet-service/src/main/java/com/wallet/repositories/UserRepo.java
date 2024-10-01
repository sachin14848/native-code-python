package com.wallet.repositories;

import com.wallet.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserName(String username);
    Optional<UserInfo> findByEmailId(String emailId);

}
