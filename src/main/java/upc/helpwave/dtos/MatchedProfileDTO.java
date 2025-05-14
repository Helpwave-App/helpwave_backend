package upc.helpwave.dtos;

import java.util.List;

public class MatchedProfileDTO {
    private int idProfile;
    private List<String> tokens;

    public MatchedProfileDTO(int idProfile, List<String> tokens) {
        this.idProfile = idProfile;
        this.tokens = tokens;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}
