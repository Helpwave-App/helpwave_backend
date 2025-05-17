package upc.helpwave.serviceinterfaces;

import upc.helpwave.dtos.NotificationMessageDTO;

public interface IFirebaseMessagingService {

    public String sendNotificationByToken(NotificationMessageDTO notificationMessageDTO);

}
