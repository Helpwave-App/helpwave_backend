package upc.helpwave.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.AvailabilityDTO;
import upc.helpwave.dtos.AvailabilityListDTO;
import upc.helpwave.entities.Availability;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.serviceinterfaces.IAvailabilityService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {
    @Autowired
    private IAvailabilityService aS;
    @Autowired
    private ProfileRepository pR;

    @PostMapping("/batch")
    public void registerBatch(@RequestBody AvailabilityListDTO dto) {
        Optional<Profile> profileOpt = pR.findById(dto.getIdProfile());
        if (!profileOpt.isPresent()) {
            throw new RuntimeException("Perfil no encontrado con ID: " + dto.getIdProfile());
        }

        Profile profile = profileOpt.get();
        ModelMapper m = new ModelMapper();

        List<Availability> list = dto.getAvailabilities().stream().map(a -> {
            Availability availability = m.map(a, Availability.class);
            availability.setProfile(profile);
            return availability;
        }).collect(Collectors.toList());

        aS.insertAll(list);
    }
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
