package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Level;
@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
}
