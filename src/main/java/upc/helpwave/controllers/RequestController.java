package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.dtos.RequestDTO;
import upc.helpwave.dtos.RequestResponseDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.SkillRepository;
import upc.helpwave.serviceimplements.FirebaseMessagingServiceImplement;
import upc.helpwave.serviceinterfaces.IEmpairingService;
import upc.helpwave.serviceinterfaces.IRequestService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    private IRequestService rS;
    @Autowired
    private IEmpairingService eS;
    @Autowired
    private ProfileRepository pR;
    @Autowired
    private SkillRepository sR;
    @Autowired
    private FirebaseMessagingServiceImplement fMS;

    @GetMapping("/history")
    public ResponseEntity<List<RequestDTO>> getRequestHistory() {
        List<RequestDTO> history = rS.findRequestHistory();
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        rS.delete(id);
    }

    @GetMapping("/{id}")
    public RequestDTO listId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        RequestDTO dto = m.map(rS.listId(id), RequestDTO.class);
        return dto;
    }

    @GetMapping
    public List<RequestDTO> list() {
        return rS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, RequestDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping
    public void update(@RequestBody RequestDTO dto) {
        ModelMapper m = new ModelMapper();
        Request a = m.map(dto, Request.class);
        rS.insert(a);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RequestDTO dto) {
        Optional<Profile> profileOpt = pR.findById(dto.getIdProfile());
        Optional<Skill> skillOpt = sR.findById(dto.getIdSkill());

        if (!profileOpt.isPresent() || !skillOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Perfil o habilidad no encontrados.");
        }

        Profile profile = profileOpt.get();

        LocalDateTime haceUnMinuto = LocalDateTime.now(ZoneId.of("America/Lima")).minusMinutes(1);
        List<Request> recientes = rS.findRecentByProfile(profile.getIdProfile(), haceUnMinuto);

        if (!recientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Solo puedes registrar una solicitud por minuto.");
        }

        Request r = new Request();
        r.setProfile(profile);
        r.setTokenDevice(dto.getTokenDevice());
        r.setSkill(skillOpt.get());
        r.setDateRequest(LocalDateTime.now(ZoneId.of("America/Lima")));
        r.setStateRequest(dto.getStateRequest());

        Request savedRequest = eS.insert(r);
        List<Empairing> empairings = eS.generateEmpairings(savedRequest);
        List<String> tokens = new ArrayList<>();

        String fullName = profile.getName() + " " + profile.getLastName();

        for (Empairing empairing : empairings) {
            Profile target = empairing.getProfile();
            String skillName = savedRequest.getSkill().getSkillDesc();

            for (Device device : target.getUser().getDevices()) {
                NotificationMessageDTO message = new NotificationMessageDTO();
                message.setTokenDevice(device.getTokenDevice());
                message.setTitle("Nueva solicitud de ayuda");
                message.setBody(fullName + " necesita tu ayuda");

                Map<String, String> data = new HashMap<>();
                data.put("type", "help_request");
                data.put("idEmpairing", String.valueOf(empairing.getIdEmpairing()));
                data.put("skill", skillName);
                data.put("name", profile.getName());
                data.put("lastname", profile.getLastName());

                message.setData(data);

                fMS.sendNotificationByToken(message);
                tokens.add(device.getTokenDevice());
            }
        }

        RequestResponseDTO response = new RequestResponseDTO(savedRequest.getIdRequest(), tokens);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/cancel/{idRequest}")
    public ResponseEntity<String> cancelRequest(@PathVariable("idRequest") Integer idRequest) {
        Request request = rS.listId(idRequest);

        if (request == null || request.getIdRequest() == 0) {
            return ResponseEntity.notFound().build();
        }

        request.setStateRequest(false);
        rS.insert(request);

        return ResponseEntity.ok("Solicitud cancelada correctamente.");
    }
}
