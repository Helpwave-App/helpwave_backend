package upc.helpwave.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.AvailabilityDTO;
import upc.helpwave.dtos.AvailabilityListDTO;
import upc.helpwave.entities.Availability;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.UserRepository;
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
    @Autowired
    private UserRepository uR;

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
    @PutMapping("/profile/{id}")
    public void updateByProfile(@PathVariable("id") Integer profileId, @RequestBody AvailabilityDTO dto) {
        ModelMapper m = new ModelMapper();
        Availability availability = m.map(dto, Availability.class);

        Profile profile = new Profile();
        profile.setIdProfile(profileId);
        availability.setProfile(profile);

        aS.insert(availability);
    }
    @GetMapping("/user/{idUser}")
    public List<AvailabilityDTO> listByUser(@PathVariable("idUser") int idUser) {
        Optional<User> userOpt = uR.findById(idUser);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUser);
        }

        Profile profile = userOpt.get().getProfile();
        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado.");
        }

        List<Availability> availabilities = aS.findByProfile(profile);

        ModelMapper m = new ModelMapper();
        return availabilities.stream()
                .map(a -> m.map(a, AvailabilityDTO.class))
                .collect(Collectors.toList());
    }
}
