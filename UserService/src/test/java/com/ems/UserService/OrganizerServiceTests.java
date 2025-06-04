package com.ems.UserService;

import com.ems.UserService.Dto.DeleteRequest;
import com.ems.UserService.Dto.LoginRequest;
import com.ems.UserService.Dto.OrganizerDto;
import com.ems.UserService.Dto.TokenDetails;
import com.ems.UserService.Entity.Organizer;
import com.ems.UserService.Exceptions.InvalidPasswordException;
import com.ems.UserService.Exceptions.OrganizerAlreadyExistsException;
import com.ems.UserService.Exceptions.OrganizerNotFoundException;
import com.ems.UserService.Exceptions.UserNotFoundException;
import com.ems.UserService.Repository.OrganizerRepository;
import com.ems.UserService.Service.OrganizerService;
import com.ems.UserService.Utility.PasswordHandler;
import com.ems.UserService.Utility.Role;
import com.ems.UserService.Utility.MailUtility; 


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class OrganizerServiceTest {

    @Mock
    private OrganizerRepository organizerRepository;

    @Mock
    private MailUtility mailUtility;

    @InjectMocks
    private OrganizerService organizerService;

    private Organizer organizer;
    private OrganizerDto organizerDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        organizer = Organizer.builder()
                .organizerId(1)
                .name("Org Name")
                .email("org@example.com")
                .password(PasswordHandler.hashPassword("password"))
                .contact("1234567890")
                .organizationName("Org Inc")
                .website("www.org.com")
                .contactNumber("1234567890")
                .role(Role.ORGANIZER)
                .build();
        organizerDto = OrganizerDto.builder()
                .organizerId(1)
                .name("Org Name")
                .email("org@example.com")
                .password("password")
                .contact("1234567890")
                .organizationName("Org Inc")
                .website("www.org.com")
                .contactNumber("1234567890")
                .build();
    }

    @Test
    void registerOrganizer_success() {
        when(organizerRepository.existsByEmailIgnoreCase(organizerDto.getEmail())).thenReturn(false);
        when(organizerRepository.save(any(Organizer.class))).thenReturn(organizer);

        String result = organizerService.registerOrganizer(organizerDto);
        assertEquals("Organizer registered successfully", result);
    }

    @Test
    void registerOrganizer_alreadyExists() {
        when(organizerRepository.existsByEmailIgnoreCase(organizerDto.getEmail())).thenReturn(true);
        assertThrows(OrganizerAlreadyExistsException.class, () -> organizerService.registerOrganizer(organizerDto));
    }

    @Test
    void login_success() {
        when(organizerRepository.findByEmailIgnoreCase(organizer.getEmail())).thenReturn(Optional.of(organizer));
        LoginRequest loginRequest = new LoginRequest(organizer.getEmail(), "password");

        TokenDetails tokenDetails = organizerService.login(loginRequest);
        assertEquals(organizer.getEmail(), tokenDetails.getEmail());
    }

    @Test
    void login_wrongPassword() {
        when(organizerRepository.findByEmailIgnoreCase(organizer.getEmail())).thenReturn(Optional.of(organizer));
        LoginRequest loginRequest = new LoginRequest(organizer.getEmail(), "wrong");

        assertThrows(InvalidPasswordException.class, () -> organizerService.login(loginRequest));
    }

    @Test
    void login_notFound() {
        when(organizerRepository.findByEmailIgnoreCase(organizer.getEmail())).thenReturn(Optional.empty());
        LoginRequest loginRequest = new LoginRequest(organizer.getEmail(), "password");

        assertThrows(UserNotFoundException.class, () -> organizerService.login(loginRequest));
    }

    @Test
    void getOrganizerById_success() {
        when(organizerRepository.findById(1)).thenReturn(Optional.of(organizer));
        OrganizerDto dto = organizerService.getOrganizerById(1);
        assertEquals(organizer.getEmail(), dto.getEmail());
    }

    @Test
    void getOrganizerById_notFound() {
        when(organizerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(OrganizerNotFoundException.class, () -> organizerService.getOrganizerById(1));
    }

    @Test
    void getAllOrganizers_success() {
        when(organizerRepository.findAll()).thenReturn(List.of(organizer));
        List<OrganizerDto> organizers = organizerService.getAllOrganizers();
        assertEquals(1, organizers.size());
        assertEquals(organizer.getEmail(), organizers.get(0).getEmail());
    }

    @Test
    void getAllOrganizers_empty() {
        when(organizerRepository.findAll()).thenReturn(List.of());
        assertThrows(OrganizerNotFoundException.class, () -> organizerService.getAllOrganizers());
    }

    @Test
    void updateOrganizer_success() {
        when(organizerRepository.findById(1)).thenReturn(Optional.of(organizer));
        when(organizerRepository.save(any(Organizer.class))).thenReturn(organizer);

        OrganizerDto updateDto = OrganizerDto.builder().name("Updated Name").contact("9999999999").organizationName("New Org").website("new.org").contactNumber("8888888888").build();
        String result = organizerService.updateOrganizer(1, updateDto);
        assertEquals("Organizer details updated successfully", result);
    }

    @Test
    void updateOrganizer_notFound() {
        when(organizerRepository.findById(1)).thenReturn(Optional.empty());
        OrganizerDto updateDto = OrganizerDto.builder().name("Updated Name").build();
        assertThrows(OrganizerNotFoundException.class, () -> organizerService.updateOrganizer(1, updateDto));
    }

    @Test
    void deleteOrganizer_success() {
        DeleteRequest req = new DeleteRequest(organizer.getEmail(), "password");
        when(organizerRepository.findById(1)).thenReturn(Optional.of(organizer));
        doNothing().when(organizerRepository).delete(organizer);

        String result = organizerService.deleteOrganizer(1, req);
        assertEquals("Organizer deleted successfully", result);
    }

    @Test
    void deleteOrganizer_invalidPassword() {
        DeleteRequest req = new DeleteRequest(organizer.getEmail(), "wrong");
        when(organizerRepository.findById(1)).thenReturn(Optional.of(organizer));
        assertThrows(InvalidPasswordException.class, () -> organizerService.deleteOrganizer(1, req));
    }

    @Test
    void deleteOrganizer_notFound() {
        DeleteRequest req = new DeleteRequest(organizer.getEmail(), "password");
        when(organizerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(OrganizerNotFoundException.class, () -> organizerService.deleteOrganizer(1, req));
    }

    @Test
    void forgotPassword_success() {
        when(organizerRepository.findByEmailIgnoreCase(organizer.getEmail())).thenReturn(Optional.of(organizer));
        when(mailUtility.generateRandomPassword(10)).thenReturn("newPass123");
        when(organizerRepository.save(any(Organizer.class))).thenReturn(organizer);
        doNothing().when(mailUtility).sendPasswordResetEmail(eq(organizer.getEmail()), eq("newPass123"));

        String result = organizerService.forgotPassword(organizer.getEmail());

        assertEquals("A new password has been sent to your email.", result);
        verify(mailUtility).sendPasswordResetEmail(eq(organizer.getEmail()), eq("newPass123"));
        verify(organizerRepository).save(any(Organizer.class));
    }

    @Test
    void forgotPassword_organizerNotFound() {
        when(organizerRepository.findByEmailIgnoreCase("notfound@org.com")).thenReturn(Optional.empty());
        assertThrows(OrganizerNotFoundException.class, () -> organizerService.forgotPassword("notfound@org.com"));
    }
    
}