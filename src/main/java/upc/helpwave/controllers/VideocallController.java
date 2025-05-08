package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.VideocallDTO;
import upc.helpwave.entities.Videocall;
import upc.helpwave.serviceinterfaces.IVideocallService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/videocalls")
public class VideocallController {
    @Autowired
    private IVideocallService vS;
    @PostMapping
    public void register(@RequestBody VideocallDTO dto){
        ModelMapper m=new ModelMapper();
        Videocall r=m.map(dto, Videocall.class);
        vS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        vS.delete(id);
    }

    @GetMapping("/{id}")
    public VideocallDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        VideocallDTO dto=m.map(vS.listId(id),VideocallDTO.class);
        return dto;
    }
    @GetMapping
    public List<VideocallDTO> list(){
        return vS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,VideocallDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody VideocallDTO dto) {
        ModelMapper m = new ModelMapper();
        Videocall a=m.map(dto,Videocall.class);
        vS.insert(a);
    }
}
