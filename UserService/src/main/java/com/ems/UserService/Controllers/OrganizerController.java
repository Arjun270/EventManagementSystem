package com.ems.UserService.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ems.UserService.Dto.DeleteRequest;
import com.ems.UserService.Dto.LoginRequest;
import com.ems.UserService.Dto.OrganizerDto;
import com.ems.UserService.Dto.PasswordRequest;
import com.ems.UserService.Dto.TokenDetails;
import com.ems.UserService.Service.OrganizerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/organizers")
@AllArgsConstructor
public class OrganizerController {

    private final OrganizerService organizerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerOrganizer(@RequestBody @Valid OrganizerDto organizerDto) {
        return new ResponseEntity<>(organizerService.registerOrganizer(organizerDto), HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<TokenDetails> userLogin(@RequestBody @Valid LoginRequest loginRequest){
    	return new ResponseEntity<>(organizerService.login(loginRequest),HttpStatus.OK);
    }

    @GetMapping("getByOrganizerId/{organizerId}")
    public ResponseEntity<OrganizerDto> getOrganizerById(@PathVariable int organizerId) {
        return new ResponseEntity<>(organizerService.getOrganizerById(organizerId), HttpStatus.OK);
    }

    // Endpoint to retrieve all organizers
    @GetMapping("/getAllOrganizers")
    public ResponseEntity<List<OrganizerDto>> getAllOrganizers() {
        return new ResponseEntity<>(organizerService.getAllOrganizers(), HttpStatus.OK);
    }

    @PutMapping("update/{organizerId}")
    public ResponseEntity<String> updateOrganizer(@PathVariable int organizerId,@RequestBody @Valid OrganizerDto organizerDto) {
        return new ResponseEntity<>(organizerService.updateOrganizer(organizerId, organizerDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{organizerId}")
    public ResponseEntity<String> deleteOrganizer(@PathVariable int organizerId,@RequestBody DeleteRequest deleteRequest) {
        String message = organizerService.deleteOrganizer(organizerId,deleteRequest);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    
    @PatchMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam @Valid String email) {
		return new ResponseEntity<>(organizerService.forgotPassword(email),HttpStatus.ACCEPTED);
    }
    
}
