package upc.helpwave.dtos;

public class VideocallDTO {
    private String token;
    private String channel;
    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public VideocallDTO(String token, String channel, String name, String lastName) {
        this.token = token;
        this.channel = channel;
        this.name = name;
        this.lastName = lastName;
    }

    public VideocallDTO() {}
}
