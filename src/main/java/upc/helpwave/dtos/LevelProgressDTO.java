package upc.helpwave.dtos;

import java.math.BigDecimal;

public class LevelProgressDTO {
    private Integer assistances;
    private String currentLevel;
    private Integer missingAssistances;
    private String nextLevel;
    private BigDecimal scoreProfile;

    public Integer getAssistances() {
        return assistances;
    }

    public void setAssistances(Integer assistances) {
        this.assistances = assistances;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Integer getMissingAssistances() {
        return missingAssistances;
    }

    public void setMissingAssistances(Integer missingAssistances) {
        this.missingAssistances = missingAssistances;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public BigDecimal getScoreProfile() {
        return scoreProfile;
    }

    public void setScoreProfile(BigDecimal scoreProfile) {
        this.scoreProfile = scoreProfile;
    }
}
