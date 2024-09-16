package com.authentication.Authentication.entities.otp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "otp")
public class Otp {

    @Id
    private long id;

    @Column(name = "otp_code")
    private String otp;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "expiry_time")
    private Date expiryTime;

    @Column(name = "is_used")
    private boolean isUsed;

}
