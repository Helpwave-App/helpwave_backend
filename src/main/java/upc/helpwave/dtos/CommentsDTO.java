package upc.helpwave.dtos;

import upc.helpwave.entities.Videocall;

import java.time.LocalDateTime;

public class CommentsDTO {
    private int idComment;
    private Videocall videocall;
    private String descriptionComment;
    private int scoreVolunteer;
    private LocalDateTime dateComment;
    private int scoreVideocall;

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public Videocall getVideocall() {
        return videocall;
    }

    public void setVideocall(Videocall videocall) {
        this.videocall = videocall;
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

    public LocalDateTime getDateComment() {
        return dateComment;
    }

    public void setDateComment(LocalDateTime dateComment) {
        this.dateComment = dateComment;
    }

    public int getScoreVideocall() {
        return scoreVideocall;
    }

    public void setScoreVideocall(int scoreVideocall) {
        this.scoreVideocall = scoreVideocall;
    }
}
