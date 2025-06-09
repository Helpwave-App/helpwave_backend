package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.LanguageDTO;
import upc.helpwave.entities.Language;
import upc.helpwave.serviceinterfaces.ILanguageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/languages")
public class LanguageController {
    @Autowired
    private ILanguageService lS;
    @PostMapping
    public void register(@RequestBody LanguageDTO dto){
        ModelMapper m=new ModelMapper();
        Language r=m.map(dto, Language.class);
        lS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        lS.delete(id);
    }
    @GetMapping("/{id}")
    public LanguageDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        LanguageDTO dto=m.map(lS.listId(id),LanguageDTO.class);
        return dto;
    }
    @GetMapping
    public List<LanguageDTO> list(){
        return lS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,LanguageDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody LanguageDTO dto) {
        ModelMapper m = new ModelMapper();
        Language a=m.map(dto,Language.class);
        lS.insert(a);
    }
}
