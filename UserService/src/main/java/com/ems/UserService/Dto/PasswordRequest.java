package com.ems.UserService.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
	@NotBlank
	@Email(message = "Enter a valid email")
	private String email;
	
	@NotBlank
	private String code;
	
	@NotBlank
	private String password;
}

