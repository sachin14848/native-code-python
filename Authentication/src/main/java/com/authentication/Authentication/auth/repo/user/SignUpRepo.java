package com.authentication.Authentication.auth.repo.user;

import com.authentication.Authentication.auth.entities.user.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignUpRepo extends JpaRepository<SignUp, Long> {
    Optional<SignUp> findByUserName(String userName);
    Optional<SignUp> findByEmailId(String emailId);
    Optional<SignUp> findByPhone(String phone);
}
