package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "LanguageProfile")
public class LanguageProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLanguageProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idLanguage", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProfile", nullable = false)
    private Profile profile;

    public LanguageProfile() {
    }

    public LanguageProfile(int idLanguageProfile, Language language, Profile profile) {
        this.idLanguageProfile = idLanguageProfile;
        this.language = language;
        this.profile = profile;
    }

    public int getIdLanguageProfile() {
        return idLanguageProfile;
    }

    public void setIdLanguageProfile(int idLanguageProfile) {
        this.idLanguageProfile = idLanguageProfile;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
