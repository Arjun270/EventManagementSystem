package com.ems.UserService.Utility;

import com.ems.UserService.Dto.OrganizerDto;
import com.ems.UserService.Entity.Organizer;

public class OrganizerEntityDtoConversion {

    public static OrganizerDto entityToDto(Organizer organizer) {
        return OrganizerDto.builder()
                .organizerId(organizer.getOrganizerId())
                .name(organizer.getName())
                .email(organizer.getEmail())
                .contact(organizer.getContact())
                .organizationName(organizer.getOrganizationName())
                .website(organizer.getWebsite())
                .contactNumber(organizer.getContactNumber())
                .build();
    }

    public static Organizer dtoToEntity(OrganizerDto organizerDto) {
        return Organizer.builder()
                .name(organizerDto.getName())
                .email(organizerDto.getEmail().toLowerCase())
                .password(PasswordHandler.hashPassword(organizerDto.getPassword()))
                .contact(organizerDto.getContact())
                .organizationName(organizerDto.getOrganizationName())
                .website(organizerDto.getWebsite())
                .contactNumber(organizerDto.getContactNumber())
                .build();
    }
}

