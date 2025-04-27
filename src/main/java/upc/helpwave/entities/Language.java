package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLanguage;

    @Column(name = "nameLanguage", nullable = false, length = 35)
    private String nameLanguage;

    public Language() {
    }

    public Language(int idLanguage, String nameLanguage) {
        this.idLanguage = idLanguage;
        this.nameLanguage = nameLanguage;
    }

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public void setNameLanguage(String nameLanguage) {
        this.nameLanguage = nameLanguage;
    }
}