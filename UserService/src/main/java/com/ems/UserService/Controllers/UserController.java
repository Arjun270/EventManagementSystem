package com.ems.UserService.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.UserService.Dto.DeleteRequest;
import com.ems.UserService.Dto.LoginRequest;
import com.ems.UserService.Dto.TokenDetails;
import com.ems.UserService.Dto.UserDto;
import com.ems.UserService.Service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	//Create
	@PostMapping("/register")
	public ResponseEntity<String> saveUser(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.saveUser(userDto),HttpStatus.CREATED);
    }
	
    //userLogin
    @PostMapping("/login")
    public ResponseEntity<TokenDetails> userLogin(@RequestBody @Valid LoginRequest loginRequest){
    	return new ResponseEntity<>(userService.login(loginRequest),HttpStatus.OK);
    }
	
	//Read
	@GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable  @Min(1) int userId) {
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }
	
	@GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
	
	@GetMapping("/getUserByEmail")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam @Email @NotBlank String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }
    
    //Update
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable @Min(1) int userId, @RequestBody @Valid UserDto user) {
        return new ResponseEntity<>(userService.updateUser(userId, user),HttpStatus.ACCEPTED);
    }
    
    //Delete
    @DeleteMapping("/deleteUserById/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable @Min(1) int userId,@RequestBody DeleteRequest deleteRequest) {
        return new ResponseEntity<>(userService.deleteUser(userId,deleteRequest),HttpStatus.OK);
    }
    
    @PatchMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam @Valid String email) {
		return new ResponseEntity<>(userService.forgotPassword(email),HttpStatus.ACCEPTED);
    }

    
}

    
	

