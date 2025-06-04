package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.dtos.VideocallDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.serviceimplements.FirebaseMessagingServiceImplement;
import upc.helpwave.serviceinterfaces.IVideocallService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/videocalls")
public class VideocallController {
    @Autowired
    private IVideocallService vS;
    @Autowired
    private FirebaseMessagingServiceImplement fMS;
    @Autowired
    private ProfileRepository pR;
    @GetMapping("/empairings/{id}")
    public ResponseEntity<VideocallDTO> findVideocallByEmpairingId(@PathVariable("id") Integer idEmpairing) {
        Optional<Videocall> videoOpt = vS.findByEmpairingId(idEmpairing);

        if (!videoOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ModelMapper m = new ModelMapper();
        VideocallDTO dto = m.map(videoOpt.get(), VideocallDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public void register(@RequestBody VideocallDTO dto){
        ModelMapper m=new ModelMapper();
        Videocall r=m.map(dto, Videocall.class);
        vS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        vS.delete(id);
    }

    @GetMapping("/{id}")
    public VideocallDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        VideocallDTO dto=m.map(vS.listId(id),VideocallDTO.class);
        return dto;
    }
    @GetMapping
    public List<VideocallDTO> list(){
        return vS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,VideocallDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody VideocallDTO dto) {
        ModelMapper m = new ModelMapper();
        Videocall a=m.map(dto,Videocall.class);
        vS.insert(a);
    }
    @PostMapping("/end")
    public ResponseEntity<String> endVideocall(@RequestBody Map<String, String> payload) {
        String channel = payload.get("channel");

        if (channel == null || channel.isEmpty()) {
            return ResponseEntity.badRequest().body("Channel is required.");
        }

        Optional<Videocall> videocallOpt = vS.findByChannel(channel);
        if (!videocallOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Videocall videocall = videocallOpt.get();
        videocall.setEndVideocall(LocalDateTime.now(ZoneId.of("America/Lima")));
        vS.insert(videocall);

        String idVideocallStr = String.valueOf(videocall.getIdVideocall());

        Empairing empairing = videocall.getEmpairing();
        Request request = empairing.getRequest();

        User requester = request.getProfile().getUser();
        if (requester != null && requester.getDevices() != null) {
            for (Device device : requester.getDevices()) {
                NotificationMessageDTO message = new NotificationMessageDTO();
                message.setTokenDevice(device.getTokenDevice());
                message.setData(Map.of(
                        "type", "videocall_end",
                        "idVideocall", idVideocallStr
                ));
                fMS.sendSilentNotificationByToken(message);
            }
        }

        Profile volunteerProfile = empairing.getProfile();
        User volunteer = volunteerProfile.getUser();
        if (volunteer != null && volunteer.getDevices() != null) {
            for (Device device : volunteer.getDevices()) {
                NotificationMessageDTO message = new NotificationMessageDTO();
                message.setTokenDevice(device.getTokenDevice());
                message.setData(Map.of(
                        "type", "videocall_end",
                        "idVideocall", idVideocallStr
                ));
                fMS.sendSilentNotificationByToken(message);
            }
        }

        return ResponseEntity.ok("Videollamada finalizada.");
    }
}
