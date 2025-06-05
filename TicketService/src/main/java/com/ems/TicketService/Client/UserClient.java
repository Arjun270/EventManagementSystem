package com.ems.TicketService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ems.TicketService.Dto.UserDto;

@FeignClient(name = "UserService")
public interface UserClient {
	
	@GetMapping("users/getByUserId/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int userId); 
	
}