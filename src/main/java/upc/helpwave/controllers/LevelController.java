package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.LevelDTO;
import upc.helpwave.entities.Level;
import upc.helpwave.serviceinterfaces.ILevelService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/levels")
public class LevelController {
    @Autowired
    private ILevelService lS;
    @PostMapping
    public void register(@RequestBody LevelDTO dto){
        ModelMapper m=new ModelMapper();
        Level r=m.map(dto, Level.class);
        lS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        lS.delete(id);
    }

    @GetMapping("/{id}")
    public LevelDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        LevelDTO dto=m.map(lS.listId(id),LevelDTO.class);
        return dto;
    }
    @GetMapping
    public List<LevelDTO> list(){
        return lS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,LevelDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody LevelDTO dto) {
        ModelMapper m = new ModelMapper();
        Level a=m.map(dto,Level.class);
        lS.insert(a);
    }
}
