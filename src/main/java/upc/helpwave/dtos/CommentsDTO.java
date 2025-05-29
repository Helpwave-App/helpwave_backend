package upc.helpwave.dtos;

public class CommentsDTO {
    private int idVideocall;
    private String descriptionComment;
    private int scoreVolunteer;
    private int scoreVideocall;

    public int getIdVideocall() {
        return idVideocall;
    }

    public void setIdVideocall(int idVideocall) {
        this.idVideocall = idVideocall;
    }

    public String getDescriptionComment() {
        return descriptionComment;
    }

    public void setDescriptionComment(String descriptionComment) {
        this.descriptionComment = descriptionComment;
    }

    public int getScoreVolunteer() {
        return scoreVolunteer;
    }

    public void setScoreVolunteer(int scoreVolunteer) {
        this.scoreVolunteer = scoreVolunteer;
    }

    public int getScoreVideocall() {
        return scoreVideocall;
    }

    public void setScoreVideocall(int scoreVideocall) {
        this.scoreVideocall = scoreVideocall;
    }
}
    }
}
