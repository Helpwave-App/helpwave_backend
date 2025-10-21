package upc.helpwave.serviceimplements;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import upc.helpwave.dtos.RequestDTO;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Request;
import upc.helpwave.entities.User;
import upc.helpwave.entities.Videocall;
import upc.helpwave.repositories.EmpairingRepository;
import upc.helpwave.repositories.RequestRepository;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.repositories.VideocallRepository;
import upc.helpwave.serviceinterfaces.IRequestService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImplement implements IRequestService {
    @Autowired
    private RequestRepository rR;
    @Autowired
    private UserRepository uR;
    @Autowired
    private EmpairingRepository eR;
    @Autowired
    private VideocallRepository vR;

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
    public List<RequestDTO> findRequestHistory() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = uR.findByUsername(userDetails.getUsername());
        List<Request> requests = new ArrayList<>();

        // Assuming role names are "REQUESTER" and "VOLUNTEER"
        if ("REQUESTER".equalsIgnoreCase(user.getRole().getRole())) {
            requests = rR.findByProfile(user.getProfile());
        } else if ("VOLUNTEER".equalsIgnoreCase(user.getRole().getRole())) {
            List<Empairing> empairings = eR.findByProfile(user.getProfile());
            requests = empairings.stream().map(Empairing::getRequest).collect(Collectors.toList());
        }

        ModelMapper modelMapper = new ModelMapper();
        List<RequestDTO> requestDTOs = new ArrayList<>();

        for (Request request : requests) {
            RequestDTO dto = modelMapper.map(request, RequestDTO.class);
            dto.setDateRequest(request.getDateRequest());
            dto.setSkillDescription(request.getSkill().getSkillDesc());

            // Calculate duration
            Empairing empairing = eR.findByRequest(request);

            if (empairing != null) {
                Optional<Videocall> videocallOpt = vR.findByEmpairing(empairing);
                if (videocallOpt.isPresent()) {
                    Videocall videocall = videocallOpt.get();
                    if (videocall.getStartVideocall() != null && videocall.getEndVideocall() != null) {
                        long seconds = java.time.Duration
                                .between(videocall.getStartVideocall(), videocall.getEndVideocall()).getSeconds();
                        dto.setDuration(seconds + " seg");
                    } else {
                        dto.setDuration("0 seg");
                    }
                } else {
                    dto.setDuration("0 seg");
                }
            } else {
                dto.setDuration("0 seg");
            }
            requestDTOs.add(dto);
        }
        return requestDTOs;
    }

    @Override
    public List<Request> findRecentByProfile(int idProfile, LocalDateTime threshold) {
        return rR.findRecentByProfile(idProfile, threshold);
    }

    @Override
    public List<Request> findPendingRequestsOlderThan(LocalDateTime threshold) {
        return rR.findTrulyPendingRequestsOlderThan(threshold);
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
