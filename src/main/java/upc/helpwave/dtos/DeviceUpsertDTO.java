package upc.helpwave.dtos;

public class DeviceUpsertDTO {
    private Integer idUser;
    private String tokenDevice;
    private String oldTokenDevice;
    private String newTokenDevice;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }

    public String getOldTokenDevice() {
        return oldTokenDevice;
    }

    public void setOldTokenDevice(String oldTokenDevice) {
        this.oldTokenDevice = oldTokenDevice;
    }

    public String getNewTokenDevice() {
        return newTokenDevice;
    }

    public void setNewTokenDevice(String newTokenDevice) {
        this.newTokenDevice = newTokenDevice;
    }
}
