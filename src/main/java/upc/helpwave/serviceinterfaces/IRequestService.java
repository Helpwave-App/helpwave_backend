package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Request;

import java.time.LocalDateTime;
import upc.helpwave.dtos.RequestDTO;

import java.util.List;

public interface IRequestService {
    void insert(Request request);

    List<Request> list();

    List<Request> findRecentByProfile(int idProfile, LocalDateTime threshold);

    List<Request> findPendingRequestsOlderThan(LocalDateTime threshold);

    Request listId(Integer idRequest);

    List<RequestDTO> findRequestHistory();

    void delete(Integer idRequest);
}
