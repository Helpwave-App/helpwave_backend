package upc.helpwave;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@SpringBootApplication
public class HelpWaveApplication {

    @Bean
    FirebaseMessaging firebaseMessaging() throws Exception {
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        if (credentialsPath == null) {
            throw new IllegalStateException(
                    "La variable de entorno GOOGLE_APPLICATION_CREDENTIALS no está configurada.");
        }

        InputStream googleCredentials = new FileInputStream(credentialsPath);

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(googleCredentials))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
        return FirebaseMessaging.getInstance(app);
    }

    public static void main(String[] args) {
        SpringApplication.run(HelpWaveApplication.class, args);
    }

}
