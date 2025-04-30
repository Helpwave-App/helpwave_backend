package upc.helpwave.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.SkillDTO;
import upc.helpwave.entities.Skill;
import upc.helpwave.serviceinterfaces.ISkillService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skills")
public class SkillController {
    @Autowired
    private ISkillService sS;
    @PostMapping
    public void register(@RequestBody SkillDTO dto){
        ModelMapper m=new ModelMapper();
        Skill r=m.map(dto, Skill.class);
        sS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        sS.delete(id);
    }

    @GetMapping("/{id}")
    public SkillDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        SkillDTO dto=m.map(sS.listId(id),SkillDTO.class);
        return dto;
    }
    @GetMapping
    public List<SkillDTO> list(){
        return sS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,SkillDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody SkillDTO dto) {
        ModelMapper m = new ModelMapper();
        Skill a=m.map(dto,Skill.class);
        sS.insert(a);
    }
}
