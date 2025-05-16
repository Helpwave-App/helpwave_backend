package upc.helpwave.dtos;

import java.util.Map;

import lombok.Data;

@Data
public class NotificationMessageDTO {
    private String tokenDevice;
    private String title;
    private String body;
    private String image;
    private Map<String, String> data;
}
