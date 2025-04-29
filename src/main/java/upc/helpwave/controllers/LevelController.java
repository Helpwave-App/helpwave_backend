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
    public void registrar(@RequestBody LevelDTO dto) {
        ModelMapper m = new ModelMapper();
        Level r = m.map(dto, Level.class);
        lS.insert(r);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        lS.delete(id);
    }

    @GetMapping("/{id}")
    public LevelDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        LevelDTO dto = m.map(lS.listId(id), LevelDTO.class);
        return dto;
    }

    @GetMapping
    public List<LevelDTO> listar() {
        return lS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, LevelDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping
    public void modificar(@RequestBody LevelDTO dto) {
        ModelMapper m = new ModelMapper();
        Level a = m.map(dto, Level.class);
        lS.insert(a);
    }
}
