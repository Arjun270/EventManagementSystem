package com.ems.UserService.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ems.UserService.Dto.DeleteRequest;
import com.ems.UserService.Dto.LoginRequest;
import com.ems.UserService.Dto.TokenDetails;
import com.ems.UserService.Dto.UserDto;
import com.ems.UserService.Entity.User;
import com.ems.UserService.Exceptions.InvalidPasswordException;
import com.ems.UserService.Exceptions.InvalidRequestException;
import com.ems.UserService.Exceptions.UserAlreadyExistsException;
import com.ems.UserService.Exceptions.UserNotFoundException;
import com.ems.UserService.Repository.UserRepository;
import com.ems.UserService.Utility.MailUtility;
import com.ems.UserService.Utility.PasswordHandler;
import com.ems.UserService.Utility.Role;
import com.ems.UserService.Utility.UserEntityDtoConversion;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserRepository userRepository;
    private final MailUtility mailUtility;

    
    // Create a new user
    public String saveUser(UserDto userDto) {
        if (userRepository.existsByEmailIgnoreCase(userDto.getEmail())) {
            logger.warn("Email already exists: {}", userDto.getEmail());
            throw new UserAlreadyExistsException("Email already exists: " + userDto.getEmail());
        }
        
        logger.info("Saving new User with EmailID: {}", userDto.getEmail());
        User userEntity = UserEntityDtoConversion.dtoToEntity(userDto);
        userRepository.save(userEntity);
        return "User saved successfully";
    }
    
    // Login endpoint: returns token details if credentials are correct
    public TokenDetails login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail()).orElseThrow(() -> {
            logger.warn("User not found with email: {}", loginRequest.getEmail());
            return new UserNotFoundException("User not found with email: " + loginRequest.getEmail());
        });
        
        if (PasswordHandler.matches(loginRequest.getPassword(), user.getPassword())) {
            logger.info("Login successful for email: {}", loginRequest.getEmail());
            TokenDetails tokenDetails = new TokenDetails(user.getName(), user.getEmail(), user.getRole().name());
            return tokenDetails;
        }
        
        logger.warn("Invalid password for email: {}", loginRequest.getEmail());
        throw new InvalidPasswordException("Please verify your password");
    }

    // Get user by ID
    public UserDto getUserById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.warn("User not found with ID: {}", userId);
            return new UserNotFoundException("User not found with ID: " + userId);
        });
        
        logger.info("User found with ID: {}", userId);
        return UserEntityDtoConversion.entityToDto(user); 
    }
    
    // Return role by user ID
    public String getRoleById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.warn("User not found with ID: {}", userId);
            return new UserNotFoundException("User not found with ID: " + userId);
        });
        
        logger.info("Role of userId {} returned: {}", user.getUserId(), user.getRole().name());
        return user.getRole().name();
    }

    // Get all users
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            logger.error("No users found in the system");
            throw new UserNotFoundException("No users found in the system.");
        }
        
        logger.info("User list was retrieved");
        return users.stream().map(UserEntityDtoConversion::entityToDto).toList(); 
    }
    
    // Get user by email
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> {
            logger.warn("User not found with email: {}", email);
            return new UserNotFoundException("User not found with email: " + email);
        });
        
        logger.info("User found with email: {}", email);
        return UserEntityDtoConversion.entityToDto(user);
    }
    
    // Get users by role
    public List<UserDto> getUsersByRole(String role) {
        List<String> validRoles = List.of("ADMIN", "ORGANIZER", "USER");
        if (!validRoles.contains(role.toUpperCase())) {
            logger.error("Invalid role provided: {}", role);
            throw new InvalidRequestException("Invalid role: " + role);
        }
        
        Role enumRole = Role.valueOf(role.toUpperCase());
        List<User> users = userRepository.findByRole(enumRole);
        if (users.isEmpty()) {
            logger.error("No users found with role: {}", enumRole);
            throw new UserNotFoundException("No users found with role: " + role);
        }
        
        logger.info("List of users with role {} retrieved", enumRole);
        return users.stream().map(UserEntityDtoConversion::entityToDto).toList();
    }
    
    // Update user details
    public String updateUser(int userId, UserDto user) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("User not found with ID: {}", userId);
            return new UserNotFoundException("User not found with ID: " + userId);
        });
        
        // Preserve existing fields if new values are null
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setContact(user.getContact() != null ? user.getContact() : existingUser.getContact());
        
        logger.info("User details updated successfully for ID: {}", userId);
        userRepository.save(existingUser);
        return "User updated successfully!";
    }

    // Delete a user 
    public String deleteUser(int userId, DeleteRequest deleteRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.warn("User not found with ID: {}", userId);
            return new UserNotFoundException("User not found with ID: " + userId);
        });
        
        if (PasswordHandler.matches(deleteRequest.getPassword(), user.getPassword())
                && user.getEmail().equalsIgnoreCase(deleteRequest.getEmail())) {
            logger.info("User deleted successfully with ID: {}", userId);
            userRepository.delete(user);
            return "User deleted successfully!";
        }
        
        logger.warn("Invalid UserId, Email or Password for deletion");
        throw new InvalidRequestException("Invalid UserId, Email or Password. Please verify before retrying");
    }
    
    // Forgot password 
    public String forgotPassword(String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        String newPassword = mailUtility.generateRandomPassword(10);
        user.setPassword(PasswordHandler.hashPassword(newPassword));
        userRepository.save(user);

        mailUtility.sendPasswordResetEmail(user.getEmail(), newPassword);

        logger.info("Password reset and email sent to: {}", email);
        return "A new password has been sent to your email.";
    }


}

