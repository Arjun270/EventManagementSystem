package com.ems.UserService;

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
import com.ems.UserService.Service.UserService;
import com.ems.UserService.Utility.PasswordHandler;
import com.ems.UserService.Utility.Role;
import com.ems.UserService.Utility.UserEntityDtoConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .userId(1)
                .name("Test User")
                .email("test@example.com")
                .password(PasswordHandler.hashPassword("password"))
                .contact("1234567890")
                .role(Role.USER)
                .build();
        userDto = UserDto.builder()
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .contact("1234567890")
                .build();
    }

    @Test
    void saveUser_success() {
        when(userRepository.existsByEmailIgnoreCase(userDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userService.saveUser(userDto);
        assertEquals("User saved successfully", result);
    }

    @Test
    void saveUser_alreadyExists() {
        when(userRepository.existsByEmailIgnoreCase(userDto.getEmail())).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }

    @Test
    void login_success() {
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
        LoginRequest loginRequest = new LoginRequest(user.getEmail(), "password");

        TokenDetails tokenDetails = userService.login(loginRequest);
        assertEquals(user.getEmail(), tokenDetails.getEmail());
    }

    @Test
    void login_wrongPassword() {
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
        LoginRequest loginRequest = new LoginRequest(user.getEmail(), "wrong");

        assertThrows(InvalidPasswordException.class, () -> userService.login(loginRequest));
    }

    @Test
    void login_userNotFound() {
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.empty());
        LoginRequest loginRequest = new LoginRequest(user.getEmail(), "password");

        assertThrows(UserNotFoundException.class, () -> userService.login(loginRequest));
    }

    @Test
    void getUserById_success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        UserDto dto = userService.getUserById(1);
        assertEquals(user.getEmail(), dto.getEmail());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void getRoleById_success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        String role = userService.getRoleById(1);
        assertEquals("USER", role);
    }

    @Test
    void getRoleById_notFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getRoleById(1));
    }

    @Test
    void getAllUsers_success() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDto> users = userService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals(user.getEmail(), users.get(0).getEmail());
    }

    @Test
    void getAllUsers_empty() {
        when(userRepository.findAll()).thenReturn(List.of());
        assertThrows(UserNotFoundException.class, () -> userService.getAllUsers());
    }

    @Test
    void getUserByEmail_success() {
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
        UserDto dto = userService.getUserByEmail(user.getEmail());
        assertEquals(user.getEmail(), dto.getEmail());
    }

    @Test
    void getUserByEmail_notFound() {
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(user.getEmail()));
    }

    @Test
    void getUsersByRole_success() {
        when(userRepository.findByRole(Role.USER)).thenReturn(List.of(user));
        List<UserDto> users = userService.getUsersByRole("USER");
        assertEquals(1, users.size());
    }

    @Test
    void getUsersByRole_invalidRole() {
        assertThrows(InvalidRequestException.class, () -> userService.getUsersByRole("INVALID"));
    }

    @Test
    void getUsersByRole_noUsers() {
        when(userRepository.findByRole(Role.USER)).thenReturn(List.of());
        assertThrows(UserNotFoundException.class, () -> userService.getUsersByRole("USER"));
    }

    @Test
    void updateUser_success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updateDto = UserDto.builder().name("Updated Name").contact("9999999999").build();
        String result = userService.updateUser(1, updateDto);
        assertEquals("User updated successfully!", result);
    }

    @Test
    void updateUser_notFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        UserDto updateDto = UserDto.builder().name("Updated Name").build();
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1, updateDto));
    }

    @Test
    void deleteUser_success() {
        DeleteRequest req = new DeleteRequest(user.getEmail(), "password");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        String result = userService.deleteUser(1, req);
        assertEquals("User deleted successfully!", result);
    }

    @Test
    void deleteUser_invalidRequest() {
        DeleteRequest req = new DeleteRequest("wrong@example.com", "wrong");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertThrows(InvalidRequestException.class, () -> userService.deleteUser(1, req));
    }

    @Test
    void deleteUser_notFound() {
        DeleteRequest req = new DeleteRequest(user.getEmail(), "password");
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1, req));
    }
}