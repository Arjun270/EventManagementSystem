package com.ems.UserService.Entity;


import com.ems.UserService.Utility.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organizers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organizerId;
    
    private String name;
    private String email;
    private String password;
    private String contact;
    
    private String organizationName;
    private String website;
    private String contactNumber;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ORGANIZER;
}
