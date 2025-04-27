package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "StateReport")
public class StateReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStateReport;

    @Column(name = "descriptionState", nullable = false, length = 35)
    private String descriptionState;

    public StateReport() {
    }

    public StateReport(int idStateReport, String descriptionState) {
        this.idStateReport = idStateReport;
        this.descriptionState = descriptionState;
    }

    public int getIdStateReport() {
        return idStateReport;
    }

    public void setIdStateReport(int idStateReport) {
        this.idStateReport = idStateReport;
    }

    public String getDescriptionState() {
        return descriptionState;
    }

    public void setDescriptionState(String descriptionState) {
        this.descriptionState = descriptionState;
    }
}
