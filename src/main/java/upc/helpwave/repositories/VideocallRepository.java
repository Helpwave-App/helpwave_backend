package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Videocall;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideocallRepository extends JpaRepository<Videocall, Integer> {
    Optional<Videocall> findByEmpairing(Empairing empairing);
}
