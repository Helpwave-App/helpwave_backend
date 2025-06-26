package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.EmpairingDTO;
import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.dtos.VideocallDTO;
import upc.helpwave.entities.*;
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
    private FirebaseMessagingServiceImplement fMS;
    @Autowired
    private IRequestService rS;

    @PostMapping
    public void register(@RequestBody EmpairingDTO dto) {
        ModelMapper m = new ModelMapper();
        Empairing r = m.map(dto, Empairing.class);
        eS.insert(r);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        eS.delete(id);
    }

    @GetMapping("/{id}")
    public EmpairingDTO listId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        EmpairingDTO dto = m.map(eS.listId(id), EmpairingDTO.class);
        return dto;
    }

    @GetMapping
    public List<EmpairingDTO> list() {
        return eS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, EmpairingDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping
    public void update(@RequestBody EmpairingDTO dto) {
        ModelMapper m = new ModelMapper();
        Empairing a = m.map(dto, Empairing.class);
        eS.insert(a);
    }

    @PostMapping("/accept/{empairingId}")
    public ResponseEntity<VideocallDTO> acceptEmpairing(@PathVariable int empairingId) {
        Empairing empairing = eS.listId(empairingId);
        if (empairing == null) {
            return ResponseEntity.notFound().build();
        }

        Request request = empairing.getRequest();
        if (request == null || !request.getStateRequest()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Videocall videocall = eS.acceptEmpairing(empairingId);
        if (videocall != null) {
            User user = request.getProfile().getUser();

            String tokenDevice = null;
            if (user != null && user.getDevices() != null && !user.getDevices().isEmpty()) {
                tokenDevice = user.getDevices().get(0).getTokenDevice();
            }

            Profile accepter = empairing.getProfile();

            if (tokenDevice != null && !tokenDevice.isEmpty()) {
                NotificationMessageDTO message = new NotificationMessageDTO();
                message.setTokenDevice(tokenDevice);
                message.setData(Map.of(
                        "type", "videocall_start",
                        "channel", videocall.getChannel(),
                        "token", videocall.getToken(),
                        "name", accepter.getName(),
                        "lastname", accepter.getLastName()));

                String response = fMS.sendSilentNotificationByToken(message);
                if (response == null) {
                    System.err.println("Error enviando notificación silenciosa al dispositivo " + tokenDevice);
                }
            }

            VideocallDTO dto = new VideocallDTO(
                    videocall.getToken(),
                    videocall.getChannel(),
                    accepter.getName(),
                    accepter.getLastName());

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
