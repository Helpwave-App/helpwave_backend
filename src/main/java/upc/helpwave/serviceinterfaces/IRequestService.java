package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Request;

import java.time.LocalDateTime;
import java.util.List;

public interface IRequestService {
    public void insert(Request request);

    public void delete(Integer idRequest);

    public Request listId(Integer idRequest);

    public List<Request> list();

    List<Request> findRecentByProfile(int idProfile, LocalDateTime threshold);

    List<Request> findPendingRequestsOlderThan(LocalDateTime threshold);
}
