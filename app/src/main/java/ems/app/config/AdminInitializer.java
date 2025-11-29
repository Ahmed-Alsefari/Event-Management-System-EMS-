//package ems.app.config;
//
//import ems.app.model.User;
//import ems.app.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class AdminInitializer {
//
//    @Bean
//    public CommandLineRunner initAdmin(UserRepository repo, PasswordEncoder encoder) {
//        return args -> {
//
//            if (!repo.existsByEmail("admin@ems.com")) {
//
//                User admin = new User();
//                admin.setName("Admin");
//                admin.setEmail("admin@ems.com");
//                admin.setPassword(encoder.encode("admin123")); // ENCODED password
//                admin.setRole("ADMIN");
//
//                repo.save(admin);
//                System.out.println("✔ Default ADMIN user created: admin@ems.com / admin123");
//            }
//        };
//    }
//}
