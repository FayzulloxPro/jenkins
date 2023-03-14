package com.tafakkoor.e_learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
