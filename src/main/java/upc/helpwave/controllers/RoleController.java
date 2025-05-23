package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.RoleDTO;
import upc.helpwave.entities.Role;
import upc.helpwave.serviceinterfaces.IRoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private IRoleService rS;
    @PostMapping
    public void register(@RequestBody RoleDTO dto){
        ModelMapper m=new ModelMapper();
        Role r=m.map(dto, Role.class);
        rS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id)
    {
        rS.delete(id);
    }
    @GetMapping("/{id}")
    public RoleDTO listId(@PathVariable("id")Long id){
        ModelMapper m=new ModelMapper();
        RoleDTO dto=m.map(rS.listId(id),RoleDTO.class);
        return dto;
    }
    @GetMapping
    public List<RoleDTO> list(){
        return rS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,RoleDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody RoleDTO dto) {
        ModelMapper m = new ModelMapper();
        Role a=m.map(dto,Role.class);
        rS.insert(a);
    }
}
