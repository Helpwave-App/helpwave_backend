package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Request;
import upc.helpwave.entities.Videocall;
import upc.helpwave.repositories.AvailabilityRepository;
import upc.helpwave.repositories.EmpairingRepository;
import upc.helpwave.serviceinterfaces.IEmpairingService;
import upc.helpwave.serviceinterfaces.IVideocallService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class EmpairingServiceImplement implements IEmpairingService {
    @Autowired
    private EmpairingRepository eR;
    @Autowired
    private AvailabilityRepository aR;
    @Autowired
    private VideocallServiceImplement vS;

    @Override
    public void insert(Empairing empairing) {
        eR.save(empairing);
    }

    @Override
    public void delete(Integer idEmpairing) {
        eR.deleteById(idEmpairing);
    }

    @Override
    public Empairing listId(Integer idEmpairing) {
        return eR.findById(idEmpairing).orElse(new Empairing());
    }

    @Override
    public List<Empairing> list() {
        return eR.findAll();
    }

    @Override
    public List<Profile> generateEmpairings(Request request) {
        LocalDateTime requestDateTime = request.getDateRequest();
        String day = String.valueOf(requestDateTime.getDayOfWeek().getValue());
        LocalTime time = requestDateTime.toLocalTime();

        List<Profile> availableProfiles = aR.findProfilesAvailableAtWithSkill(day, time, request.getSkill().getIdSkill());

        for (Profile profile : availableProfiles) {
            Empairing empairing = new Empairing();
            empairing.setRequest(request);
            empairing.setProfile(profile);
            empairing.setStateEmpairing(false);
            eR.save(empairing);
        }

        return availableProfiles;
    }

    public Videocall acceptEmpairing(int empairingId) {
        Empairing empairing = listId(empairingId);
        if (empairing != null) {
            empairing.setStateEmpairing(true);
            eR.save(empairing);

            return vS.createVideocall(empairing);
        }
        return null;
    }
}
