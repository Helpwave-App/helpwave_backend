package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Request;

import java.util.List;

public interface IEmpairingService {
    public void insert(Empairing empairing);

    public void delete(Integer idEmpairing);

    public Empairing listId(Integer idEmpairing);

    public List<Empairing> list();

    List<Profile> generateEmpairings(Request request);
}
