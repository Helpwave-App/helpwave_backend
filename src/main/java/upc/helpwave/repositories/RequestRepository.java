package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByProfile(Profile profile);

    @Query("SELECT r FROM Request r WHERE r.profile.idProfile = :idProfile AND r.dateRequest > :threshold")
    List<Request> findRecentByProfile(@Param("idProfile") int idProfile, @Param("threshold") LocalDateTime threshold);

    List<Request> findByStateRequestTrueAndDateRequestBefore(LocalDateTime threshold);

    @Query("SELECT r FROM Request r WHERE r.stateRequest = true AND r.dateRequest < :threshold AND NOT EXISTS (SELECT e FROM Empairing e WHERE e.request = r AND e.stateEmpairing = true)")
    List<Request> findTrulyPendingRequestsOlderThan(@Param("threshold") LocalDateTime threshold);
}
