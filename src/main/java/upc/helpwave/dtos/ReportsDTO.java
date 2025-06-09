package upc.helpwave.dtos;

public class ReportsDTO {
    private int idVideocall;
    private int idTypeReport;
    private String descriptionReport;

    public int getIdVideocall() {
        return idVideocall;
    }

    public void setIdVideocall(int idVideocall) {
        this.idVideocall = idVideocall;
    }

    public int getIdTypeReport() {
        return idTypeReport;
    }

    public void setIdTypeReport(int idTypeReport) {
        this.idTypeReport = idTypeReport;
    }

    public String getDescriptionReport() {
        return descriptionReport;
    }

    public void setDescriptionReport(String descriptionReport) {
        this.descriptionReport = descriptionReport;
    }
}
