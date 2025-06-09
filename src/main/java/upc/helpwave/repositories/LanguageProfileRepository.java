package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.LanguageProfile;
import upc.helpwave.entities.Profile;
import java.util.List;

@Repository
public interface LanguageProfileRepository extends JpaRepository<LanguageProfile, Integer> {
    @Query("SELECT lp.language.idLanguage FROM LanguageProfile lp WHERE lp.profile.idProfile = :idProfile")
    List<Integer> findLanguageIdsByProfile(@Param("idProfile") int idProfile);
    List<LanguageProfile> findByProfile(Profile profile);
}
