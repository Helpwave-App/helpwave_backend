package upc.helpwave.dtos;

public class LevelDTO {
    private int idLevel;
    private String nameLevel;
    private int minRequest;
    private int maxRequest;

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getNameLevel() {
        return nameLevel;
    }

    public void setNameLevel(String nameLevel) {
        this.nameLevel = nameLevel;
    }

    public int getMinRequest() {
        return minRequest;
    }

    public void setMinRequest(int minRequest) {
        this.minRequest = minRequest;
    }

    public int getMaxRequest() {
        return maxRequest;
    }

    public void setMaxRequest(int maxRequest) {
        this.maxRequest = maxRequest;
    }
}
