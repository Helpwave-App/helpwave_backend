package upc.helpwave.serviceinterfaces;
import upc.helpwave.entities.Availability;
import upc.helpwave.entities.Profile;

import java.util.List;

public interface IAvailabilityService{
    public void insert(Availability availability);

    public void delete(Integer idAvailability);

    public Availability listId(Integer idAvailability);

    public List<Availability> list();

    void insertAll(List<Availability> availabilities);

    List<Availability> findByProfile(Profile profile);

}
