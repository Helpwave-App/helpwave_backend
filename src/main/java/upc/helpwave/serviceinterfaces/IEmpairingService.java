package upc.helpwave.serviceinterfaces;

import upc.helpwave.dtos.MatchedProfileDTO;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Request;
import upc.helpwave.entities.Videocall;

import java.util.List;

public interface IEmpairingService {
    public void insert(Empairing empairing);
    public void delete(Integer idEmpairing);
    public Empairing listId(Integer idEmpairing);
    List<Empairing> list();
    List<MatchedProfileDTO> generateEmpairings(Request request);
    Request insert(Request request);
    public Videocall acceptEmpairing(int empairingId);
}
