package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.StateReportDTO;
import upc.helpwave.entities.StateReport;
import upc.helpwave.serviceinterfaces.IStateReportService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stateReports")
public class StateReportController {
    @Autowired
    private IStateReportService rS;
    @PostMapping
    public void register(@RequestBody StateReportDTO dto){
        ModelMapper m=new ModelMapper();
        StateReport r=m.map(dto, StateReport.class);
        rS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        rS.delete(id);
    }
    @GetMapping("/{id}")
    public StateReportDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        StateReportDTO dto=m.map(rS.listId(id),StateReportDTO.class);
        return dto;
    }
    @GetMapping
    public List<StateReportDTO> list(){
        return rS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,StateReportDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody StateReportDTO dto) {
        ModelMapper m = new ModelMapper();
        StateReport a=m.map(dto,StateReport.class);
        rS.insert(a);
    }
}
