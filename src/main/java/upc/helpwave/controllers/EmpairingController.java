package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.EmpairingDTO;
import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.dtos.VideocallDTO;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Request;
import upc.helpwave.entities.User;
import upc.helpwave.entities.Videocall;
import upc.helpwave.serviceimplements.EmpairingServiceImplement;
import upc.helpwave.serviceimplements.FirebaseMessagingServiceImplement;
import upc.helpwave.serviceinterfaces.IEmpairingService;
import upc.helpwave.serviceinterfaces.IRequestService;
import upc.helpwave.serviceinterfaces.IVideocallService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/empairings")
public class EmpairingController {
    @Autowired
    private IEmpairingService eS;
    @Autowired
    private IVideocallService vS;
    @Autowired
    private EmpairingServiceImplement eSI;
    @Autowired
    private FirebaseMessagingServiceImplement fMS;
    @Autowired
    private IRequestService rS;
    @PostMapping
    public void register(@RequestBody EmpairingDTO dto){
        ModelMapper m=new ModelMapper();
        Empairing r=m.map(dto, Empairing.class);
        eS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        eS.delete(id);
    }

    @GetMapping("/{id}")
    public EmpairingDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        EmpairingDTO dto=m.map(eS.listId(id),EmpairingDTO.class);
        return dto;
    }
    @GetMapping
    public List<EmpairingDTO> list(){
        return eS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,EmpairingDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody EmpairingDTO dto) {
        ModelMapper m = new ModelMapper();
        Empairing a=m.map(dto,Empairing.class);
        eS.insert(a);
    }
    @PostMapping("/accept/{empairingId}")
    public ResponseEntity<VideocallDTO> acceptEmpairing(@PathVariable int empairingId) {
        Videocall videocall = eSI.acceptEmpairing(empairingId);
        if (videocall != null) {
            Empairing empairing = eS.listId(empairingId);
            Request request = empairing.getRequest();
            User user = request.getProfile().getUser();

            String tokenDevice = null;
            if (user != null && user.getDevices() != null && !user.getDevices().isEmpty()) {
                tokenDevice = user.getDevices().get(0).getTokenDevice();
            }

            if (tokenDevice != null && !tokenDevice.isEmpty()) {
                NotificationMessageDTO message = new NotificationMessageDTO();
                message.setTokenDevice(tokenDevice);
                message.setTitle("Tu solicitud ha sido aceptada");
                message.setBody("Estás entrando a una videollamada con un voluntario.");
                message.setImage(null);
                message.setData(Map.of(
                        "type", "videocall_start",
                        "channel", videocall.getChannel(),
                        "token", videocall.getToken()));
                fMS.sendSilentNotificationByToken(message);
            }

            VideocallDTO dto = new VideocallDTO(videocall.getToken(), videocall.getChannel());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
