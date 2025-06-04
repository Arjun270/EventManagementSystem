package com.ems.AuthServer.Controller;

import com.ems.AuthServer.Client.UserClient;
import com.ems.AuthServer.Dto.CreateOrganizer;
import com.ems.AuthServer.Dto.CreateUser;
import com.ems.AuthServer.Dto.LoginRequest;
import com.ems.AuthServer.Dto.TokenDetails;
import com.ems.AuthServer.Security.JwtTokenProvider;
import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserClient userClient;

    @GetMapping("/Welcome")
    public String summa() {
        return "summa";
    }

    @PostMapping("/CreateUser")
    public ResponseEntity<String> createUser(@RequestBody CreateUser createUser) {
        String response = userClient.createUser(createUser).getBody();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/CreateOrganizer")
    public ResponseEntity<String> createOrganizer(@RequestBody CreateOrganizer createOrganizer) {
        String response = userClient.createOrganizer(createOrganizer).getBody();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        TokenDetails user;
        if (loginRequest.getRole().equals("ORGANIZER")) {
            user = userClient.organizerLogin(loginRequest).getBody(); 
        } else {
            user = userClient.userLogin(loginRequest).getBody();
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getName(), user.getRole());

        Map<String, Object> response = Map.of(
            "accessToken", accessToken,
            "user", user
        );
        
        return ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    } 

   
}
