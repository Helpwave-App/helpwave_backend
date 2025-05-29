package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
}
