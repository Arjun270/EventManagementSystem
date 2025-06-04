package com.ems.AuthServer.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ems.AuthServer.Dto.CreateOrganizer;
import com.ems.AuthServer.Dto.CreateUser;
import com.ems.AuthServer.Dto.LoginRequest;
import com.ems.AuthServer.Dto.TokenDetails;

import jakarta.validation.Valid;

@FeignClient(name = "UserService")
public interface UserClient {
	@PostMapping("users/login")
	public ResponseEntity<TokenDetails> userLogin(@RequestBody LoginRequest loginRequest);
	
	@PostMapping("organizers/login")
	public ResponseEntity<TokenDetails> organizerLogin(@RequestBody LoginRequest loginRequest);
	
	@PostMapping("users/register")
  	public ResponseEntity<String> createUser(@RequestBody @Valid CreateUser createRequest);
	
	@PostMapping("organizers/register")
  	public ResponseEntity<String> createOrganizer(@RequestBody @Valid CreateOrganizer createRequest);

}
