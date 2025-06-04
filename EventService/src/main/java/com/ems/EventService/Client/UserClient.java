package com.ems.EventService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ems.EventService.Dto.OrganizerDto;

@FeignClient(name = "UserService")
public interface UserClient {
	
	@GetMapping("organizers/getByOrganizerId/{organizerId}")
    public ResponseEntity<OrganizerDto> getOrganizerById(@PathVariable int organizerId); 
	
}
