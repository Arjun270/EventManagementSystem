package com.ems.EventService.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerDto {
    private int organizerId;
    
    private String name;
    private String email;
    private String contact;
    public String password;
    
    private String organizationName;
    private String website;
    private String contactNumber;
	
}