package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.AvailabilityRepository;
import upc.helpwave.repositories.EmpairingRepository;
import upc.helpwave.repositories.RequestRepository;
import upc.helpwave.repositories.VideocallRepository;
import upc.helpwave.serviceinterfaces.IEmpairingService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpairingServiceImplement implements IEmpairingService {
    @Autowired
    private EmpairingRepository eR;
    @Autowired
    private AvailabilityRepository aR;
    @Autowired
    private VideocallServiceImplement vS;
    @Autowired
    private RequestRepository rR;
    @Autowired
    private VideocallRepository vR;

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
    public List<Empairing> generateEmpairings(Request request) {
        LocalDateTime requestDateTime = request.getDateRequest();
        String day = String.valueOf(requestDateTime.getDayOfWeek().getValue());
        LocalTime time = requestDateTime.toLocalTime();

        List<Profile> allAvailable = aR.findProfilesAvailableAtWithSkill(day, time, request.getSkill().getIdSkill());
        List<Integer> usedProfileIds = eR.findProfileIdsByIdRequest(request.getIdRequest());

        List<Profile> remaining = allAvailable.stream()
                .filter(p -> !usedProfileIds.contains(p.getIdProfile()))
                .filter(p -> vR.findActiveVideocallsByProfile(p.getIdProfile()).isEmpty())
                .collect(Collectors.toList());

        Collections.shuffle(remaining);

        int batchSize = 5;
        List<Empairing> createdEmpairings = new ArrayList<>();

        for (int i = 0; i < Math.min(batchSize, remaining.size()); i++) {
            Profile profile = remaining.get(i);

            Empairing empairing = new Empairing();
            empairing.setRequest(request);
            empairing.setProfile(profile);
            empairing.setStateEmpairing(false);
            eR.save(empairing);

            createdEmpairings.add(empairing);
        }

        return createdEmpairings;
    }


    @Override
    public Videocall acceptEmpairing(int empairingId) {
        Empairing empairing = listId(empairingId);

        if (empairing == null || empairing.getRequest() == null) {
            return null;
        }

        Integer requestId = empairing.getRequest().getIdRequest();

        boolean alreadyAccepted = eR.existsByRequestIdRequestAndStateEmpairingTrue(requestId);
        if (alreadyAccepted) {
            return null;
        }

        empairing.setStateEmpairing(true);
        eR.save(empairing);

        return vS.createVideocall(empairing);
    }

    @Override
    public Request insert(Request request) {
        return rR.save(request);
    }
}
