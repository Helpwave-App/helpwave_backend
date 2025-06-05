package upc.helpwave.dtos;

import java.math.BigDecimal;

public class LevelProgressDTO {
    private Integer assistances;
    private Integer missingAssistances;
    private BigDecimal scoreProfile;
    private String currentLevel;
    private String currentLevelPhotoUrl;
    private String nextLevel;
    private String nextLevelPhotoUrl;

    public Integer getAssistances() {
        return assistances;
    }

    public void setAssistances(Integer assistances) {
        this.assistances = assistances;
    }

    public Integer getMissingAssistances() {
        return missingAssistances;
    }

    public void setMissingAssistances(Integer missingAssistances) {
        this.missingAssistances = missingAssistances;
    }

    public BigDecimal getScoreProfile() {
        return scoreProfile;
    }

    public void setScoreProfile(BigDecimal scoreProfile) {
        this.scoreProfile = scoreProfile;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getCurrentLevelPhotoUrl() {
        return currentLevelPhotoUrl;
    }

    public void setCurrentLevelPhotoUrl(String currentLevelPhotoUrl) {
        this.currentLevelPhotoUrl = currentLevelPhotoUrl;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getNextLevelPhotoUrl() {
        return nextLevelPhotoUrl;
    }

    public void setNextLevelPhotoUrl(String nextLevelPhotoUrl) {
        this.nextLevelPhotoUrl = nextLevelPhotoUrl;
    }
}
