package com.usermanagement.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateUserDto {
	@NotNull
	public Long id;
	
	@NotBlank(message = "Name is mandatory")
	@NotNull
	public String name;
	
    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Email must be filled")
	@NotNull
	public String email;
}
