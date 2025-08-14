package com.project.repair.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private int id;
    private String username;
    private String password;
    private String phone;
    private String address;
}
