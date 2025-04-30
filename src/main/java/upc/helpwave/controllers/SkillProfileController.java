package upc.helpwave.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.SkillProfileDTO;
import upc.helpwave.entities.SkillProfile;
import upc.helpwave.serviceinterfaces.ISkillProfileService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skillProfiles")
public class SkillProfileController {
    @Autowired
    private ISkillProfileService spS;
    @PostMapping
    public void register(@RequestBody SkillProfileDTO dto){
        ModelMapper m=new ModelMapper();
        SkillProfile r=m.map(dto, SkillProfile.class);
        spS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        spS.delete(id);
    }

    @GetMapping("/{id}")
    public SkillProfileDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        SkillProfileDTO dto=m.map(spS.listId(id),SkillProfileDTO.class);
        return dto;
    }
    @GetMapping
    public List<SkillProfileDTO> list(){
        return spS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,SkillProfileDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody SkillProfileDTO dto) {
        ModelMapper m = new ModelMapper();
        SkillProfile a=m.map(dto,SkillProfile.class);
        spS.insert(a);
    }
}
