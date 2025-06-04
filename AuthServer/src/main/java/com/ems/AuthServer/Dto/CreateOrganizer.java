package com.ems.AuthServer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizer {
    private String name;
    private String email;
    private String password;
    private String contact;
    
    private String organizationName;
    private String website;
    private String contactNumber;
}
