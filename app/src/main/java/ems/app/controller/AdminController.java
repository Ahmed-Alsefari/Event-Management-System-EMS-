package ems.app.controller;

import ems.app.model.ServicePackage;
import ems.app.model.Event;
import ems.app.model.Reservation;
import ems.app.service.ReservationService;
import ems.app.service.ServicePackageService;
import ems.app.service.NotificationService;
import ems.app.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final ReservationService reservationService;
    private final ServicePackageService packageService;
    private final EventRepository eventRepository;
    private final NotificationService notificationService;

    // Admin Dashboard with Statistics
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<Event> allEvents = eventRepository.findAll();

        // Calculate statistics
        int totalBookings = allEvents.size();
        int pendingCount = (int) allEvents.stream().filter(e -> "Pending".equals(e.getStatus())).count();
        int confirmedCount = (int) allEvents.stream().filter(e -> "Confirmed".equals(e.getStatus())).count();

        // Calculate revenue from confirmed events with packages
        double totalRevenue = allEvents.stream()
                .filter(e -> "Confirmed".equals(e.getStatus()) && e.getServicePackage() != null)
                .mapToDouble(e -> e.getServicePackage().getPrice())
                .sum();

        // Calculate average booking value
        double avgBookingValue = confirmedCount > 0 ? totalRevenue / confirmedCount : 0;

        // Calculate confirmation rate
        double confirmationRate = totalBookings > 0 ? (confirmedCount * 100.0 / totalBookings) : 0;

        // This month bookings
        YearMonth currentMonth = YearMonth.now();
        int thisMonthBookings = (int) allEvents.stream()
                .filter(e -> e.getDate() != null && e.getDate().startsWith(String.valueOf(currentMonth.getYear())))
                .count();

        // Revenue trend (last 6 months)
        Map<String, Double> revenueTrend = calculateRevenueTrend(allEvents);

        // Bookings trend (last 6 months)
        Map<String, Integer> bookingsTrend = calculateBookingsTrend(allEvents);

        // Recent activity (last 10 events)
        List<Event> recentActivity = allEvents.stream()
                .sorted(Comparator.comparing(Event::getId).reversed())
                .limit(10)
                .collect(Collectors.toList());

        model.addAttribute("totalBookings", totalBookings);
        model.addAttribute("totalRevenue", String.format("%.2f", totalRevenue));
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("confirmedCount", confirmedCount);
        model.addAttribute("avgBookingValue", String.format("%.2f", avgBookingValue));
        model.addAttribute("confirmationRate", String.format("%.1f", confirmationRate));
        model.addAttribute("thisMonthBookings", thisMonthBookings);
        model.addAttribute("revenueTrend", revenueTrend);
        model.addAttribute("bookingsTrend", bookingsTrend);
        model.addAttribute("recentActivity", recentActivity);

        // --- FIX START: Added this line to resolve 500 Error ---
        model.addAttribute("packages", packageService.findAll());
        // --- FIX END ---

        return "admin_dashboard";
    }

    // Service Packages Management Page
    @GetMapping("/packages")
    public String managePackages(Model model) {
        model.addAttribute("packages", packageService.findAll());
        model.addAttribute("newPackage", new ServicePackage());
        return "admin_packages";
    }

    // Add Service Package
    @PostMapping("/service/add")
    public String addService(@ModelAttribute ServicePackage service) {
        packageService.add(service);
        return "redirect:/admin/packages?added";
    }

    // Update Service Package
    @PostMapping("/service/update")
    public String updateService(@RequestParam Long id,
                                @RequestParam String name,
                                @RequestParam String description,
                                @RequestParam double price) {
        packageService.updateService(id, name, description, price);
        return "redirect:/admin/packages?updated";
    }

    // Delete Service Package
    @GetMapping("/service/delete/{id}")
    public String deleteService(@PathVariable Long id) {
        packageService.delete(id);
        return "redirect:/admin/packages?deleted";
    }

    // Toggle Package Active Status
    @GetMapping("/service/toggle/{id}")
    public String togglePackageStatus(@PathVariable Long id) {
        packageService.toggleActive(id);
        return "redirect:/admin/packages?toggled";
    }

    // All Reservations Page
    @GetMapping("/reservations")
    public String allReservations(Model model) {
        List<Event> allEvents = eventRepository.findAll();
        model.addAttribute("events", allEvents);
        return "admin_reservations";
    }

    // Reservation Details Page
    @GetMapping("/reservations/{id}")
    public String reservationDetails(@PathVariable Long id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        model.addAttribute("event", event);
        return "admin_reservation_details";
    }

    // Update Event Status with Email Notification
    @PostMapping("/events/update-status")
    public String updateEventStatus(@RequestParam Long id, @RequestParam String status) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        String oldStatus = event.getStatus();
        event.setStatus(status);
        eventRepository.save(event);

        if (!oldStatus.equals(status) && event.getUser() != null) {
            notificationService.sendEventStatusEmail(event, status);
        }

        return "redirect:/admin/reservations/" + id + "?updated";
    }

    // Delete Event
    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
        return "redirect:/admin/reservations?deleted";
    }

    // Helper method to calculate revenue trend
    private Map<String, Double> calculateRevenueTrend(List<Event> events) {
        Map<String, Double> trend = new LinkedHashMap<>();
        YearMonth currentMonth = YearMonth.now();

        for (int i = 5; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            String monthKey = month.toString();

            double revenue = events.stream()
                    .filter(e -> e.getDate() != null &&
                            e.getDate().startsWith(monthKey) &&
                            "Confirmed".equals(e.getStatus()) &&
                            e.getServicePackage() != null)
                    .mapToDouble(e -> e.getServicePackage().getPrice())
                    .sum();
            trend.put(monthKey, revenue);
        }

        return trend;
    }

    // Helper method to calculate bookings trend
    private Map<String, Integer> calculateBookingsTrend(List<Event> events) {
        Map<String, Integer> trend = new LinkedHashMap<>();
        YearMonth currentMonth = YearMonth.now();

        for (int i = 5; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            String monthKey = month.toString();

            int count = (int) events.stream()
                    .filter(e -> e.getDate() != null && e.getDate().startsWith(monthKey))
                    .count();
            trend.put(monthKey, count);
        }

        return trend;
    }
}