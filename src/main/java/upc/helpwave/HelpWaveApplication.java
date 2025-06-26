package upc.helpwave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HelpWaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpWaveApplication.class, args);
    }

}
