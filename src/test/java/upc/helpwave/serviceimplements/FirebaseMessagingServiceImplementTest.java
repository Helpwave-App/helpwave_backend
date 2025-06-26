package upc.helpwave.serviceimplements;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import upc.helpwave.dtos.NotificationMessageDTO;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirebaseMessagingServiceImplementTest {

    private FirebaseMessagingServiceImplement service;

    @BeforeEach
    void setUp() {
        service = new FirebaseMessagingServiceImplement();
    }

    @Test
    void sendNotificationByToken_shouldSendAndReturnResponse() throws Exception {
        NotificationMessageDTO dto = new NotificationMessageDTO();
        dto.setTitle("Test");
        dto.setBody("Mensaje de prueba");
        dto.setTokenDevice("fake_token");
        dto.setData(Map.of("key", "value"));

        FirebaseApp mockApp = mock(FirebaseApp.class);
        FirebaseMessaging mockMessaging = mock(FirebaseMessaging.class);

        try (MockedStatic<FirebaseApp> firebaseAppMocked = mockStatic(FirebaseApp.class);
                MockedStatic<FirebaseMessaging> firebaseMessagingMocked = mockStatic(FirebaseMessaging.class)) {

            firebaseAppMocked.when(() -> FirebaseApp.getInstance("my-app")).thenReturn(mockApp);
            firebaseMessagingMocked.when(() -> FirebaseMessaging.getInstance(mockApp)).thenReturn(mockMessaging);
            when(mockMessaging.send(any(Message.class))).thenReturn("success");

            String result = service.sendNotificationByToken(dto);

            assertEquals("success", result);
        }
    }

    @Test
    void sendSilentNotificationByToken_shouldReturnResponse() throws Exception {
        NotificationMessageDTO dto = new NotificationMessageDTO();
        dto.setTokenDevice("silent_token");
        dto.setData(Map.of("silent", "true"));

        FirebaseApp mockApp = mock(FirebaseApp.class);
        FirebaseMessaging mockMessaging = mock(FirebaseMessaging.class);

        try (MockedStatic<FirebaseApp> firebaseAppMocked = mockStatic(FirebaseApp.class);
                MockedStatic<FirebaseMessaging> firebaseMessagingMocked = mockStatic(FirebaseMessaging.class)) {

            firebaseAppMocked.when(() -> FirebaseApp.getInstance("my-app")).thenReturn(mockApp);
            firebaseMessagingMocked.when(() -> FirebaseMessaging.getInstance(mockApp)).thenReturn(mockMessaging);
            when(mockMessaging.send(any(Message.class))).thenReturn("silent_success");

            String result = service.sendSilentNotificationByToken(dto);

            assertEquals("silent_success", result);
        }
    }
}
