package com.wallet.services;

import com.wallet.entities.UserInfo;
import com.wallet.entities.WalletEntity;
import com.wallet.repositories.UserRepo;
import com.wallet.repositories.WalletRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletService {

    private final UserRepo userRepo;
    private final WalletRepo walletRepo;

    @Transactional
    public boolean createAccount(Long userId) throws Exception {
        log.info("Creating account");
        Optional<UserInfo> userInfo = userRepo.findById(userId);
        log.info("UserInfo: {}", userInfo);
        if (userInfo.isPresent()) {


            log.info("UserInfo already exists");

            UserInfo user = userInfo.get();

            // Check if the user already has a wallet
            boolean walletExists = walletRepo.existsByUser(user);

            // If the wallet exists, return false or handle it as needed
            if (walletExists) {
                throw new Exception("User already has a wallet.");
            }
            WalletEntity wallet = new WalletEntity();
            wallet.setUser(user);
            wallet.setCurrentBalance(BigDecimal.ZERO);  // Initial balance is zero

            // Save the wallet
            walletRepo.save(wallet);

            return true;
        } else {
            throw new Exception("User not found.");
        }
    }
}
