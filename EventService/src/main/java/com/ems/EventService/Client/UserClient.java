package com.ems.EventService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService")
public interface UserClient {
	
	@GetMapping("users/getRoleById/{userId}")
	public String getRoleById(@PathVariable int userId);
	
}
