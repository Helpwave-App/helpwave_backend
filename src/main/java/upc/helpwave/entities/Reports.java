package upc.helpwave.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reports")
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReport;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idVideocall", nullable = false)
    private Videocall videocall;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTypeReport", nullable = false)
    private TypeReport typeReport;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idStateReport")
    private StateReport stateReport;

    @Column(name = "descriptionReport", length = 200)
    private String descriptionReport;

    @Column(name = "dateReport", nullable = false)
    private LocalDateTime dateReport;

    public Reports() {
    }

    public Reports(int idReport, Videocall videocall, TypeReport typeReport, StateReport stateReport, String descriptionReport, LocalDateTime dateReport) {
        this.idReport = idReport;
        this.videocall = videocall;
        this.typeReport = typeReport;
        this.stateReport = stateReport;
        this.descriptionReport = descriptionReport;
        this.dateReport = dateReport;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public Videocall getVideocall() {
        return videocall;
    }

    public void setVideocall(Videocall videocall) {
        this.videocall = videocall;
    }

    public TypeReport getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(TypeReport typeReport) {
        this.typeReport = typeReport;
    }

    public StateReport getStateReport() {
        return stateReport;
    }

    public void setStateReport(StateReport stateReport) {
        this.stateReport = stateReport;
    }

    public String getDescriptionReport() {
        return descriptionReport;
    }

    public void setDescriptionReport(String descriptionReport) {
        this.descriptionReport = descriptionReport;
    }

    public LocalDateTime getDateReport() {
        return dateReport;
    }

    public void setDateReport(LocalDateTime dateReport) {
        this.dateReport = dateReport;
    }
}
