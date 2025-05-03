package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.SkillProfile;

import java.util.List;

@Repository
public interface SkillProfileRepository extends JpaRepository<SkillProfile, Integer> {
    List<SkillProfile> findByProfile(Profile profile);
}
