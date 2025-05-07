package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Request;
import upc.helpwave.repositories.RequestRepository;
import upc.helpwave.serviceinterfaces.IRequestService;

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
}
