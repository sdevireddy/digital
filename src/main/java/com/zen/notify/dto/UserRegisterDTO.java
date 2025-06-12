package com.zen.notify.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegisterDTO {
	
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
}
