package com.wallet.repositories;

import com.wallet.entities.UserInfo;
import com.wallet.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends JpaRepository<WalletEntity, Long> {

    WalletEntity findByUserId(Long id);
    boolean existsByUser(UserInfo user);
}
