package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.TypeReportDTO;
import upc.helpwave.entities.TypeReport;
import upc.helpwave.serviceinterfaces.ITypeReportService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/typeReports")
public class TypeReportController {
    @Autowired
    private ITypeReportService rS;
    @PostMapping
    public void register(@RequestBody TypeReportDTO dto){
        ModelMapper m=new ModelMapper();
        TypeReport r=m.map(dto, TypeReport.class);
        rS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        rS.delete(id);
    }
    @GetMapping("/{id}")
    public TypeReportDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        TypeReportDTO dto=m.map(rS.listId(id),TypeReportDTO.class);
        return dto;
    }
    @GetMapping
    public List<TypeReportDTO> list(){
        return rS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,TypeReportDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody TypeReportDTO dto) {
        ModelMapper m = new ModelMapper();
        TypeReport a=m.map(dto,TypeReport.class);
        rS.insert(a);
    }
}
