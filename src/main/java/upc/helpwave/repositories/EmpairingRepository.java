package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Empairing;

@Repository
public interface EmpairingRepository extends JpaRepository<Empairing, Integer> {
}
