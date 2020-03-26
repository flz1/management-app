package com.usermanagement.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Auth {
    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Email must be filled")
	@NotNull
	public String email;
    
    @NotBlank(message = "Password must be filled")
	@NotNull
	public String password;
}
