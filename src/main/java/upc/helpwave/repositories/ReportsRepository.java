package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Reports;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Integer> {
}
