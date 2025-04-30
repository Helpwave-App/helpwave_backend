package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Availability;
import upc.helpwave.repositories.AvailabilityRepository;
import upc.helpwave.serviceinterfaces.IAvailabilityService;

import java.util.List;

@Service
public class AvailabilityServiceImplement implements IAvailabilityService {
    @Autowired
    private AvailabilityRepository aR;
    @Override
    public void insert(Availability availability) {
        aR.save(availability);
    }

    @Override
    public void delete(Integer idAvailability) {
        aR.deleteById(idAvailability);
    }

    @Override
    public Availability listId(Integer idAvailability) {
        return aR.findById(idAvailability).orElse(new Availability());
    }

    @Override
    public List<Availability> list() {
        return aR.findAll();
    }
}
