package com.ems.FeedbackService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ems.FeedbackService.Dto.UserResponse;

@FeignClient(name = "UserService")
public interface UserClient {
	@GetMapping("/userDetails")
    public UserResponse getUserDetails(@RequestParam int userId);

}
