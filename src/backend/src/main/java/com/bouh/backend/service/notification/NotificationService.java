package com.bouh.backend.service.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    // Send a push notification to a specific device using its FCM token
    public void sendNotification(String fcmToken, String title, String body) {
        // If the token is missing, skip sending
        if (fcmToken == null || fcmToken.isBlank()) {
            log.warn("Cannot send notification: FCM token is null or empty");
            return;
        }

        try {
            // Build the FCM message with the target token, title, and body
            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            // Send the message via Firebase Cloud Messaging
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Notification sent successfully, response: {}", response);
        } catch (Exception e) {
            log.error("Failed to send FCM notification: {}", e.getMessage(), e);
        }
    }
    
}
