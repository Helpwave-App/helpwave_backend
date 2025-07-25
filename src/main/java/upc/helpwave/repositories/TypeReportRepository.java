package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.TypeReport;

@Repository
public interface TypeReportRepository extends JpaRepository<TypeReport, Integer> {
}
