package upc.helpwave.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idVideocall", nullable = false)
    private Videocall videocall;

    @Column(name = "descriptionComment", length = 200)
    private String descriptionComment;

    @Column(name = "scoreVolunteer", nullable = false)
    private int scoreVolunteer;

    @Column(name = "dateComment", nullable = false)
    private LocalDateTime dateComment;

    @Column(name = "scoreVideocall", nullable = false)
    private int scoreVideocall;

    public Comments() {
    }

    public Comments(int idComment, Videocall videocall, String descriptionComment, int scoreVolunteer, LocalDateTime dateComment, int scoreVideocall) {
        this.idComment = idComment;
        this.videocall = videocall;
        this.descriptionComment = descriptionComment;
        this.scoreVolunteer = scoreVolunteer;
        this.dateComment = dateComment;
        this.scoreVideocall = scoreVideocall;
    }

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
