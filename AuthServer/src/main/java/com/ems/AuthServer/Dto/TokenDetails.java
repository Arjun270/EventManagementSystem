package com.ems.AuthServer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDetails {
	private String name;
	private String email;
	private String role;
}
