package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.serviceinterfaces.IFirebaseMessagingService;

@Service
public class FirebaseMessagingServiceImplement implements IFirebaseMessagingService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendNotificationByToken(NotificationMessageDTO notificationMessageDTO) {
        Notification notification = Notification.builder()
                .setTitle(notificationMessageDTO.getTitle())
                .setBody(notificationMessageDTO.getBody())
                .setImage(notificationMessageDTO.getImage())
                .build();

        Message mesagge = Message.builder().setToken(notificationMessageDTO.getTokenDevice())
                .setNotification(notification)
                .putAllData(notificationMessageDTO.getData())
                .build();

        try {
            firebaseMessaging.send(mesagge);
            return "Notification sent successfully";
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error sending notification: " + e.getMessage();
        }
    }
}
