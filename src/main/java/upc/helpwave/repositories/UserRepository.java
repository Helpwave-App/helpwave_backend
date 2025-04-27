package upc.helpwave.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import upc.helpwave.entities.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
	//BUSCAR POR NOMBRE
	@Query("select count(u.username) from User u where u.username =:username")
	public int buscarUsername(@Param("username") String nombre);
	//INSERTAR ROLES
	@Transactional
	@Modifying
	@Query(value = "insert into roles (rol, user_id) VALUES (:rol, :user_id)", nativeQuery = true)
	public void insRol(@Param("rol") String authority, @Param("user_id") int user_id);
}