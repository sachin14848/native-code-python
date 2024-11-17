package com.authentication.Authentication.auth.repo.auth;

import com.authentication.Authentication.auth.entities.auth.UserInfoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfoEntity, Long> {
    Optional<UserInfoEntity> findByEmailId(String emailId);

    // Update email in user table
    @Modifying  // Indicate modification operation
    @Transactional
    @Query("UPDATE UserInfoEntity u SET u.password = :newPassword WHERE u.emailId = :emailId")
    int updatePasswordAndOtpExpiryData(@Param("emailId") String emailId, @Param("newPassword") String newPassword);

        // u.email = :email
    @Modifying  // Indicate modification operation
    @Transactional
    @Query("UPDATE UserInfoEntity u SET u.userName = :username, u.phone = :phoneNumber WHERE u.id = :userId")
     int updateUser(@Param("userId") int userId,
                              @Param("username") String username,
                              @Param("phoneNumber") String phoneNumber
    ); //@Param("email") String email

}
