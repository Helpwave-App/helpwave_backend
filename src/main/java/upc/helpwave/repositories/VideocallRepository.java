package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Videocall;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideocallRepository extends JpaRepository<Videocall, Integer> {
    Optional<Videocall> findByEmpairing(Empairing empairing);
    @Query("SELECT v FROM Videocall v " +
            "WHERE v.empairing.profile.idProfile = :idProfile " +
            "AND v.endVideocall > CURRENT_TIMESTAMP")
    List<Videocall> findActiveVideocallsByProfile(@Param("idProfile") int idProfile);
    Optional<Videocall> findByChannel(String channel);
}
