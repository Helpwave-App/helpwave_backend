package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.EmpairingDTO;
import upc.helpwave.dtos.VideocallDTO;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Videocall;
import upc.helpwave.serviceimplements.EmpairingServiceImplement;
import upc.helpwave.serviceinterfaces.IEmpairingService;
import upc.helpwave.serviceinterfaces.IVideocallService;

import java.util.List;
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
            VideocallDTO dto = new VideocallDTO(videocall.getToken(), videocall.getChannel());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
