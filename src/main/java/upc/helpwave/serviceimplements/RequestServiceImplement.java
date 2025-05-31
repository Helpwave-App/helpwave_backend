package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Request;
import upc.helpwave.repositories.RequestRepository;
import upc.helpwave.serviceinterfaces.IRequestService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class RequestServiceImplement implements IRequestService {
    @Autowired
    private RequestRepository rR;

    @Override
    public void insert(Request request) {
        rR.save(request);
    }

    @Override
    public void delete(Integer idRequest) {
        rR.deleteById(idRequest);
    }

    @Override
    public Request listId(Integer idRequest) {
        return rR.findById(idRequest).orElse(new Request());
    }

    @Override
    public List<Request> list() {
        return rR.findAll();
    }

    @Override
    public List<Request> findRecentByProfile(int idProfile, LocalDateTime threshold) {
        return rR.findRecentByProfile(idProfile, threshold);
    }

    @Override
    public List<Request> findPendingRequestsOlderThan(LocalDateTime threshold) {
        return rR.findByStateRequestTrueAndDateRequestBefore(threshold);
    }

    @Scheduled(fixedRate = 60000)
    public void expireUnansweredRequests() {
        LocalDateTime threshold = LocalDateTime.now(ZoneId.of("America/Lima")).minusMinutes(5);
        List<Request> expired = findPendingRequestsOlderThan(threshold);

        for (Request r : expired) {
            r.setStateRequest(false);
            insert(r);
        }

        if (!expired.isEmpty()) {
            System.out.println("Solicitudes expiradas automáticamente: " + expired.size());
        }
    }
}
