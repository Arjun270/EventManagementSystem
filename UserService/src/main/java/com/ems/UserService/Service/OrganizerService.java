package com.ems.UserService.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ems.UserService.Dto.DeleteRequest;
import com.ems.UserService.Dto.LoginRequest;
import com.ems.UserService.Dto.OrganizerDto;
import com.ems.UserService.Dto.PasswordRequest;
import com.ems.UserService.Dto.TokenDetails;
import com.ems.UserService.Entity.Organizer;
import com.ems.UserService.Exceptions.InvalidPasswordException;
import com.ems.UserService.Exceptions.OrganizerAlreadyExistsException;
import com.ems.UserService.Exceptions.OrganizerNotFoundException;
import com.ems.UserService.Exceptions.UserNotFoundException;
import com.ems.UserService.Repository.OrganizerRepository;
import com.ems.UserService.Utility.MailUtility;
import com.ems.UserService.Utility.OrganizerEntityDtoConversion;
import com.ems.UserService.Utility.PasswordHandler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrganizerService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizerService.class);
    
    private final OrganizerRepository organizerRepository;
    private final MailUtility mailUtility;
    
    // Register a new organizer
    public String registerOrganizer(OrganizerDto organizerDto) {
    	if(organizerRepository.existsByEmailIgnoreCase(organizerDto.getEmail())) {
            logger.warn("Organizer not found with Email ID: {}", organizerDto.getEmail());
    		throw new OrganizerAlreadyExistsException("Organizer already exists with Email ID "+organizerDto.getEmail());
    	}
        Organizer organizer = OrganizerEntityDtoConversion.dtoToEntity(organizerDto);
        organizerRepository.save(organizer);
        logger.info("Organizer registered successfully with email: {}", organizer.getEmail());
        return "Organizer registered successfully";
    }
    
    //details for token - login
    public TokenDetails login(LoginRequest loginRequest) {
        Organizer organizer = organizerRepository.findByEmailIgnoreCase(loginRequest.getEmail()).orElseThrow(() -> {
            logger.warn("Organizer not found with email: {}", loginRequest.getEmail());
            return new UserNotFoundException("User not found with email: " + loginRequest.getEmail());
        });
        
        if (PasswordHandler.matches(loginRequest.getPassword(), organizer.getPassword())) {
            logger.info("Login successful for email: {}", loginRequest.getEmail());
            TokenDetails tokenDetails = new TokenDetails(organizer.getOrganizationName(), organizer.getEmail(), organizer.getRole().name());
            return tokenDetails;
        }
        
        logger.warn("Invalid password for email: {}", loginRequest.getEmail());
        throw new InvalidPasswordException("Please verify your password");
    }

    // Get organizer by ID
    public OrganizerDto getOrganizerById(int organizerId) {
        Organizer organizer = organizerRepository.findById(organizerId).orElseThrow(() -> {
            logger.warn("Organizer not found with ID: {}", organizerId);
            return new OrganizerNotFoundException("Organizer not found with ID: " + organizerId);
        });
        logger.info("Organizer found with ID: {}", organizerId);
        return OrganizerEntityDtoConversion.entityToDto(organizer);
    }

    // Get all organizers
    public List<OrganizerDto> getAllOrganizers() {
        List<Organizer> organizers = organizerRepository.findAll();
        if (organizers.isEmpty()) {
            logger.error("No organizers found in the system");
            throw new OrganizerNotFoundException("No organizers found in the system.");
        }
        logger.info("Organizer list retrieved successfully");
        return organizers.stream().map(OrganizerEntityDtoConversion::entityToDto).toList();
    }

    // Update organizer details
    public String updateOrganizer(int organizerId, OrganizerDto organizerDto) {
        Organizer organizer = organizerRepository.findById(organizerId).orElseThrow(() -> {
            logger.warn("Organizer not found with ID: {}", organizerId);
            return new OrganizerNotFoundException("Organizer not found with ID: " + organizerId);
        });
        
        // Update organizer-specific fields accordingly
        organizer.setName(organizerDto.getName());
        organizer.setContact(organizerDto.getContact());
        organizer.setOrganizationName(organizerDto.getOrganizationName());
        organizer.setWebsite(organizerDto.getWebsite());
        organizer.setContactNumber(organizerDto.getContactNumber());
        
        organizerRepository.save(organizer);
        logger.info("Organizer details updated for ID: {}", organizerId);
        return "Organizer details updated successfully";
    }

    //Delete - Organizer
    public String deleteOrganizer(int organizerId, DeleteRequest deleteRequest) {
        Organizer organizer = organizerRepository.findById(organizerId).orElseThrow(() -> {
            logger.warn("Organizer not found with ID: {}", organizerId);
            return new OrganizerNotFoundException("Organizer not found with ID: " + organizerId);
        });

        if (PasswordHandler.matches(deleteRequest.getPassword(), organizer.getPassword())) {
            organizerRepository.delete(organizer);
            logger.info("Organizer deleted successfully with ID: {}", organizerId);
            return "Organizer deleted successfully";
        }
        logger.warn("Password mismatch for organizer ID: {}", organizerId);
        throw new InvalidPasswordException("Incorrect password provided");
    }

    public String forgotPassword(@Valid String email) {
        Organizer organizer = organizerRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new OrganizerNotFoundException("Organizer not found with email: " + email));

        String newPassword = mailUtility.generateRandomPassword(10);
        organizer.setPassword(PasswordHandler.hashPassword(newPassword));
        organizerRepository.save(organizer);

        mailUtility.sendPasswordResetEmail(organizer.getEmail(), newPassword);

        logger.info("Password reset and email sent to organizer: {}", email);
        return "A new password has been sent to your email.";
    }

}
