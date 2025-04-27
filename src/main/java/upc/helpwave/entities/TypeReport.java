package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "TypeReport")
public class TypeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTypeReport;

    @Column(name = "typeDesc", nullable = false, length = 30)
    private String typeDesc;

    public TypeReport() {
    }

    public TypeReport(int idTypeReport, String typeDesc) {
        this.idTypeReport = idTypeReport;
        this.typeDesc = typeDesc;
    }

    public int getIdTypeReport() {
        return idTypeReport;
    }

    public void setIdTypeReport(int idTypeReport) {
        this.idTypeReport = idTypeReport;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
