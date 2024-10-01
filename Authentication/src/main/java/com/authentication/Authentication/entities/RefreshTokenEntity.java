package com.authentication.Authentication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REFRESH_TOKENS")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "REFRESH_TOKEN", nullable = false, length = 1000, unique = false)
    private String refreshToken;

    @Column(name = "REVOKED")
    private boolean revoked;

    private String domainName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserInfoEntity user;



}
