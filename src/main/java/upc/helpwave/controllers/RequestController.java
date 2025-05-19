package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.MatchedProfileDTO;
import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.dtos.RequestDTO;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Request;
import upc.helpwave.entities.Skill;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.SkillRepository;
import upc.helpwave.serviceimplements.FirebaseMessagingServiceImplement;
import upc.helpwave.serviceinterfaces.IEmpairingService;
import upc.helpwave.serviceinterfaces.IRequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        rS.delete(id);
    }

    @GetMapping("/{id}")
    public RequestDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        RequestDTO dto=m.map(rS.listId(id),RequestDTO.class);
        return dto;
    }
    @GetMapping
    public List<RequestDTO> list(){
        return rS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,RequestDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody RequestDTO dto) {
        ModelMapper m = new ModelMapper();
        Request a=m.map(dto,Request.class);
        rS.insert(a);
    }
    @PostMapping
    public ResponseEntity<List<String>> register(@RequestBody RequestDTO dto) {
        Optional<Profile> profileOpt = pR.findById(dto.getIdProfile());
        Optional<Skill> skillOpt = sR.findById(dto.getIdSkill());

        if (!profileOpt.isPresent() || !skillOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Request r = new Request();
        r.setProfile(profileOpt.get());
        r.setSkill(skillOpt.get());
        r.setDateRequest(LocalDateTime.now(ZoneId.of("America/Lima")));
        r.setStateRequest(dto.getStateRequest());

        Request savedRequest = eS.insert(r);
        List<String> tokens = eS.generateEmpairings(savedRequest);

        for (String token : tokens) {
            NotificationMessageDTO message = new NotificationMessageDTO();
            message.setTokenDevice(token);
            message.setTitle("Nueva solicitud de ayuda");
            message.setBody("Hay una solicitud de videollamada pendiente que puedes aceptar.");
            message.setImage(null);
            message.setData(Map.of("type", "request", "idRequest", String.valueOf(savedRequest.getIdRequest())));
            fMS.sendNotificationByToken(message);
        }

        return ResponseEntity.ok(tokens);
    }
}
