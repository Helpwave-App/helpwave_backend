package upc.helpwave.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.AvailabilityDTO;
import upc.helpwave.entities.Availability;
import upc.helpwave.serviceinterfaces.IAvailabilityService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {
    @Autowired
    private IAvailabilityService aS;
    @PostMapping
    public void register(@RequestBody AvailabilityDTO dto){
        ModelMapper m=new ModelMapper();
        Availability r=m.map(dto, Availability.class);
        aS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        aS.delete(id);
    }

    @GetMapping("/{id}")
    public AvailabilityDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        AvailabilityDTO dto=m.map(aS.listId(id),AvailabilityDTO.class);
        return dto;
    }
    @GetMapping
    public List<AvailabilityDTO> list(){
        return aS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,AvailabilityDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody AvailabilityDTO dto) {
        ModelMapper m = new ModelMapper();
        Availability a=m.map(dto,Availability.class);
        aS.insert(a);
    }
}
