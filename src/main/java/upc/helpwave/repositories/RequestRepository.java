package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
}
