package upc.helpwave.serviceinterfaces;
import upc.helpwave.entities.Availability;

import java.util.List;

public interface IAvailabilityService{
    public void insert(Availability availability);

    public void delete(Integer idAvailability);

    public Availability listId(Integer idAvailability);

    public List<Availability> list();
}
