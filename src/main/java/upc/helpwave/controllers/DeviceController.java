package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.DeviceDTO;
import upc.helpwave.entities.Device;
import upc.helpwave.serviceinterfaces.IDeviceService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private IDeviceService dS;
    @DeleteMapping("/{token}")
    public void delete(@PathVariable("token") String token) {
        dS.delete(token);
    }
    @GetMapping("/{id}")
    public DeviceDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        DeviceDTO dto=m.map(dS.listId(id),DeviceDTO.class);
        return dto;
    }
    @GetMapping
    public List<DeviceDTO> list(){
        return dS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,DeviceDTO.class);
        }).collect(Collectors.toList());
    }
    @PostMapping
    public void register(@RequestBody DeviceDTO dto) {
        ModelMapper m = new ModelMapper();
        Device r = m.map(dto, Device.class);
        r.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Lima")));
        dS.insert(r);
    }

    @PutMapping
    public void update(@RequestBody DeviceDTO dto) {
        ModelMapper m = new ModelMapper();
        Device a = m.map(dto, Device.class);
        a.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Lima")));
        dS.insert(a);
    }
}
