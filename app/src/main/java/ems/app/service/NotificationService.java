package ems.app.service;

import ems.app.model.Event;
import ems.app.model.Notification;
import ems.app.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public Notification sendNotification(Notification n) {
        Notification saved = notificationRepository.save(n);

        if (mailSender != null) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(n.getUser().getEmail());
                msg.setSubject(n.getSubject());
                msg.setText(n.getMessage());
                mailSender.send(msg);
            } catch (Exception e) {
                System.err.println("Failed to send email: " + e.getMessage());
            }
        }

        return saved;
    }

    // SEND EVENT STATUS EMAIL - FIXED: Now uses actual customer email
    public void sendEventStatusEmail(Event event, String status) {
        if (mailSender == null) {
            System.out.println("⚠️ Mail sender not configured. Email not sent.");
            return;
        }

        try {
            SimpleMailMessage msg = new SimpleMailMessage();

            // FIXED: Get actual customer email from the event's user
            String customerEmail = event.getUser() != null ? event.getUser().getEmail() : null;

            if (customerEmail == null) {
                System.err.println("❌ Cannot send email: Customer email not found for event ID " + event.getId());
                return;
            }

            msg.setTo(customerEmail);
            msg.setSubject("Event Booking Update: " + event.getTitle() + " - Status: " + status);

            String customerName = event.getUser().getName();

            String messageText = String.format(
                    "Dear %s,\n\n" +
                            "We would like to inform you about the status update for your event booking:\n\n" +
                            "EVENT DETAILS:\n" +
                            "• Event: %s\n" +
                            "• Date: %s\n" +
                            "• Location: %s\n" +
                            "• Number of Attendees: %d\n" +
                            "• Current Status: %s\n\n" +
                            "If you have any questions or need further assistance, please don't hesitate to contact us.\n\n" +
                            "Best regards,\n" +
                            "Event Management System Team\n" +
                            "EMS Support",
                    customerName,
                    event.getTitle(),
                    event.getDate(),
                    event.getLocation(),
                    event.getSeats(),
                    status
            );

            msg.setText(messageText);
            mailSender.send(msg);

            System.out.println("✅ Email sent successfully to " + customerEmail + "!");
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }

    public List<Notification> findByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}