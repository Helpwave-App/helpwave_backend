package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
}
