package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
}
