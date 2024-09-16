package com.testService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String emailId;
    private String roles;

}
