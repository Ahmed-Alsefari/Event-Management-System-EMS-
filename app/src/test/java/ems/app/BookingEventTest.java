package ems.app;

import ems.app.controller.EventController;
import ems.app.model.Event;
import ems.app.model.User;
import ems.app.model.ServicePackage;
import ems.app.repository.EventRepository;
import ems.app.repository.ServicePackageRepository;
import ems.app.service.UserService;
import ems.app.service.ServicePackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookingEventTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ServicePackageService packageService;

    @Mock
    private ServicePackageRepository servicePackageRepository;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private EventController eventController;

    private User testUser;
    private Event testEvent;
    private ServicePackage testPackage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setName("Test User");

        testPackage = new ServicePackage();
        testPackage.setId(1L);
        testPackage.setName("Premium Package");
        testPackage.setPrice(1000.0);
        testPackage.setDescription("Premium event package");

        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setTitle("Test Event");
        testEvent.setDescription("Test Description");
        testEvent.setDate("2024-12-31");
        testEvent.setLocation("Test Location");
        testEvent.setSeats(50);
    }

    @Test
    void testSaveEvent_Success() {
        // Arrange - Setup the test conditions
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(servicePackageRepository.findById(1L)).thenReturn(Optional.of(testPackage));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // Act - Execute the method we're testing
        String viewName = eventController.saveEvent(testEvent, 1L, userDetails);

        // Assert - Verify the results
        assertEquals("redirect:/dashboard/profile?eventCreated", viewName);
        verify(eventRepository).save(any(Event.class));
        verify(userService).findByEmail("test@example.com");
        verify(servicePackageRepository).findById(1L);
    }

    @Test
    void testSaveEvent_UserNotFound() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("nonexistent@example.com");
        when(userService.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            eventController.saveEvent(testEvent, 1L, userDetails);
        });

        verify(userService).findByEmail("nonexistent@example.com");
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void testSaveEvent_ServicePackageNotFound() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(servicePackageRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            eventController.saveEvent(testEvent, 999L, userDetails);
        });

        verify(userService).findByEmail("test@example.com");
        verify(servicePackageRepository).findById(999L);
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void testBookPage_Success() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // Act
        String viewName = eventController.bookPage(userDetails, model);

        // Assert
        assertEquals("customer/book", viewName);
        verify(model).addAttribute(eq("user"), eq(testUser));
        verify(model).addAttribute(eq("event"), any(Event.class));
        verify(packageService).findAll();
    }

    @Test
    void testCancelEvent_Success() {
        // Arrange
        testEvent.setUser(testUser);
        testEvent.setStatus("Pending");

        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        // Act
        String viewName = eventController.cancelEvent(1L, userDetails);

        // Assert
        assertEquals("redirect:/dashboard/profile?eventCancelled", viewName);
        assertEquals("Cancelled", testEvent.getStatus());
        verify(eventRepository).save(testEvent);
    }
}