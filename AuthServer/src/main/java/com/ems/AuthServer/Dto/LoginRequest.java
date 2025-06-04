package com.ems.AuthServer.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotEmpty
	@Email(message = "Email must a valid mail")
	private String email;
	
	@NotBlank(message = "Password must not be blank")
    private String password;
	
	private String role;
}
