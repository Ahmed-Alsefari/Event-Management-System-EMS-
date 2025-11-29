package ems.app.controller;

import ems.app.model.User;
import ems.app.service.ReservationService;
import ems.app.service.UserService;
import ems.app.service.ServicePackageService;
import ems.app.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final ServicePackageService packageService;
    private final EventRepository eventRepository;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }
        userService.register(user);
        return "redirect:/login?registered";
    }

    // HOME PAGE
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";

        String email = userDetails.getUsername();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        return "customer/home";
    }

    // SERVICES PAGE
    @GetMapping("/dashboard/services")
    public String services(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";

        String email = userDetails.getUsername();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("packages", packageService.findAll());
        return "customer/services";
    }

    // PROFILE PAGE - FIXED: Only show user's own events
    @GetMapping("/dashboard/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";

        String email = userDetails.getUsername();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // FIXED: Get only THIS user's events, not all events
        model.addAttribute("user", user);
        model.addAttribute("events", eventRepository.findByUserId(user.getId()));
        return "customer/profile";
    }

    // Update Profile
    @PostMapping("/dashboard/profile/update")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam String name,
                                @RequestParam String phone) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(name);
        user.setPhone(phone);
        userService.updateUser(user);

        return "redirect:/dashboard/profile?updated";
    }
}