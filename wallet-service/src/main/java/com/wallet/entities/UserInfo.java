package com.wallet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserInfo {

    @Id
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private WalletEntity wallet;

    private String userName;
    private String emailId;
    private String roles;



}
