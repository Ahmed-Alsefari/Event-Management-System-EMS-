package ems.app;

import ems.app.controller.AdminController;
import ems.app.model.Event;
import ems.app.model.User;
import ems.app.model.ServicePackage;
import ems.app.repository.EventRepository;
import ems.app.service.ReservationService;
import ems.app.service.ServicePackageService;
import ems.app.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReservationManagementTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private ServicePackageService packageService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    private Event testEvent;
    private User testUser;
    private ServicePackage testPackage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("customer@example.com");
        testUser.setName("Test Customer");

        testPackage = new ServicePackage();
        testPackage.setId(1L);
        testPackage.setName("Premium Package");
        testPackage.setPrice(1000.0);

        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setTitle("Corporate Event");
        testEvent.setDescription("Annual corporate meeting");
        testEvent.setDate("2024-12-31");
        testEvent.setLocation("Conference Hall");
        testEvent.setSeats(100);
        testEvent.setUser(testUser);
        testEvent.setServicePackage(testPackage);
        testEvent.setStatus("Pending");
    }

    @Test
    void testAllReservations_Success() {
        // Arrange
        List<Event> events = Arrays.asList(testEvent);
        when(eventRepository.findAll()).thenReturn(events);

        // Act
        String viewName = adminController.allReservations(model);

        // Assert
        assertEquals("admin_reservations", viewName);
        verify(model).addAttribute(eq("events"), eq(events));
        verify(eventRepository).findAll();
    }

    @Test
    void testReservationDetails_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        // Act
        String viewName = adminController.reservationDetails(1L, model);

        // Assert
        assertEquals("admin_reservation_details", viewName);
        verify(model).addAttribute(eq("event"), eq(testEvent));
        verify(eventRepository).findById(1L);
    }

    @Test
    void testUpdateEventStatus_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // Act
        String viewName = adminController.updateEventStatus(1L, "Confirmed");

        // Assert
        assertEquals("redirect:/admin/reservations/1?updated", viewName);
        assertEquals("Confirmed", testEvent.getStatus());
        verify(notificationService).sendEventStatusEmail(testEvent, "Confirmed");
        verify(eventRepository).save(testEvent);
    }

    @Test
    void testDeleteEvent_Success() {
        // Arrange
        doNothing().when(eventRepository).deleteById(1L);

        // Act
        String viewName = adminController.deleteEvent(1L);

        // Assert
        assertEquals("redirect:/admin/reservations?deleted", viewName);
        verify(eventRepository).deleteById(1L);
    }
}