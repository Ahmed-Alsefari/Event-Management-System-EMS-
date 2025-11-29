package ems.app;

import ems.app.controller.UserController;
import ems.app.model.User;
import ems.app.model.Event;
import ems.app.repository.EventRepository;
import ems.app.service.UserService;
import ems.app.service.ServicePackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProfileAndReservationsTest {

    @Mock
    private UserService userService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ServicePackageService packageService;

    @Mock
    private Model model;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private Event testEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setName("Test User");
        testUser.setPhone("1234567890");

        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setTitle("Test Event");
        testEvent.setStatus("Pending");
        testEvent.setUser(testUser);
    }

    @Test
    void testProfile_Success() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(eventRepository.findByUserId(1L)).thenReturn(Arrays.asList(testEvent));

        // Act
        String viewName = userController.profile(userDetails, model);

        // Assert
        assertEquals("customer/profile", viewName);
        verify(model).addAttribute(eq("user"), eq(testUser));
        verify(model).addAttribute(eq("events"), anyList());
        verify(eventRepository).findByUserId(1L);
    }

    @Test
    void testUpdateProfile_Success() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(userService.updateUser(any(User.class))).thenReturn(testUser);

        // Act
        String viewName = userController.updateProfile(userDetails, "Updated Name", "9876543210");

        // Assert
        assertEquals("redirect:/dashboard/profile?updated", viewName);
        assertEquals("Updated Name", testUser.getName());
        assertEquals("9876543210", testUser.getPhone());
        verify(userService).updateUser(testUser);
    }

    @Test
    void testDashboard_Success() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // Act
        String viewName = userController.dashboard(userDetails, model);

        // Assert
        assertEquals("customer/home", viewName);
        verify(model).addAttribute(eq("user"), eq(testUser));
    }

    @Test
    void testRegister_Success() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("new@example.com");
        newUser.setPassword("password");
        newUser.setName("New User");

        when(userService.existsByEmail("new@example.com")).thenReturn(false);
        when(userService.register(any(User.class))).thenReturn(newUser);

        // Act
        String viewName = userController.register(newUser, model);

        // Assert
        assertEquals("redirect:/login?registered", viewName);
        verify(userService).register(newUser);
        verify(userService).existsByEmail("new@example.com");
    }
}