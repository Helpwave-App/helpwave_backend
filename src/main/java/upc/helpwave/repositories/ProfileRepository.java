package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
