package upc.helpwave.dtos;
import upc.helpwave.entities.StateReport;
import upc.helpwave.entities.TypeReport;
import upc.helpwave.entities.Videocall;

import java.time.LocalDateTime;

public class ReportsDTO {
    private int idReport;
    private Videocall videocall;
    private TypeReport typeReport;
    private StateReport stateReport;
    private String typeReportDescription;
    private String descriptionReport;
    private LocalDateTime dateReport;

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

    public String getTypeReportDescription() {
        return typeReportDescription;
    }

    public void setTypeReportDescription(String typeReportDescription) {
        this.typeReportDescription = typeReportDescription;
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
