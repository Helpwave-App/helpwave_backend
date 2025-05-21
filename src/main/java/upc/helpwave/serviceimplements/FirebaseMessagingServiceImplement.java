package upc.helpwave.serviceimplements;

import com.google.firebase.FirebaseApp;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.serviceinterfaces.IFirebaseMessagingService;

@Service
public class FirebaseMessagingServiceImplement implements IFirebaseMessagingService {
    public String sendNotificationByToken(NotificationMessageDTO dto) {
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(dto.getTokenDevice())
                .setNotification(notification)
                .putAllData(dto.getData())
                .build();

        try {
            String response = FirebaseMessaging.getInstance(FirebaseApp.getInstance("my-app"))
                    .send(message);
            return response;
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String sendSilentNotificationByToken(NotificationMessageDTO dto) {
        Message message = Message.builder()
                .setToken(dto.getTokenDevice())
                .putAllData(dto.getData())
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .putHeader("apns-priority", "5")
                        .setAps(Aps.builder()
                                .setContentAvailable(true)
                                .build())
                        .build())
                .build();

        try {
            return FirebaseMessaging.getInstance(FirebaseApp.getInstance("my-app"))
                    .send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
