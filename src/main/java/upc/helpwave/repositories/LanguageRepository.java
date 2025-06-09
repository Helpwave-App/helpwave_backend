package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
