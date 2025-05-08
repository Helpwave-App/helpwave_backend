package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Availability;
import upc.helpwave.entities.Profile;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    List<Availability> findByProfile(Profile profile);
    @Query("SELECT a.profile FROM Availability a " +
            "JOIN SkillProfile sp ON sp.profile = a.profile " +
            "WHERE a.day = :day " +
            "AND :time BETWEEN a.hourStart AND a.hourEnd " +
            "AND sp.skill.idSkill = :skillId")
    List<Profile> findProfilesAvailableAtWithSkill(@Param("day") String day,
                                                   @Param("time") LocalTime time,
                                                   @Param("skillId") Integer skillId);
}

