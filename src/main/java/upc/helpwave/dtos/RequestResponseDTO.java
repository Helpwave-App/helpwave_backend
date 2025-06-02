package upc.helpwave.dtos;

import java.util.List;

public class RequestResponseDTO {
    private int idRequest;
    private List<String> tokens;

    public RequestResponseDTO() {
    }

    public RequestResponseDTO(int idRequest, List<String> tokens) {
        this.tokens = tokens;
        this.idRequest = idRequest;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}
