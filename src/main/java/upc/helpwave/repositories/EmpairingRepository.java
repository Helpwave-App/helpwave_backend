package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Request;

import upc.helpwave.entities.Profile;

import java.util.List;

@Repository
public interface EmpairingRepository extends JpaRepository<Empairing, Integer> {
    public abstract boolean existsByRequestIdRequestAndStateEmpairingTrue(Integer idRequest);
    @Query("SELECT e.profile.idProfile FROM Empairing e WHERE e.request.idRequest = :idRequest")
    List<Integer> findProfileIdsByIdRequest(@Param("idRequest") Integer idRequest);
    List<Empairing> findByProfile(Profile profile);
    List<Empairing> findByRequest(Request request);
}
