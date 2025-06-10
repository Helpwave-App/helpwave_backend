package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.ReportsDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.StateReportRepository;
import upc.helpwave.repositories.TypeReportRepository;
import upc.helpwave.repositories.VideocallRepository;
import upc.helpwave.serviceinterfaces.IReportsService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private IReportsService rS;
    @Autowired
    private VideocallRepository vR;

    @Autowired
    private TypeReportRepository tR;

    @Autowired
    private StateReportRepository sR;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody ReportsDTO dto) {
        Optional<Videocall> videocallOpt = vR.findById(dto.getIdVideocall());
        Optional<TypeReport> typeReportOpt = tR.findById(dto.getIdTypeReport());
        Optional<StateReport> stateReportOpt = sR.findById(1);

        if (!videocallOpt.isPresent() || !typeReportOpt.isPresent() || !stateReportOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Videocall, TypeReport o StateReport no encontrados.");
        }

        Videocall videocall = videocallOpt.get();
        Empairing empairing = videocall.getEmpairing();

        if (empairing == null || empairing.getRequest() == null || empairing.getProfile() == null) {
            return ResponseEntity.badRequest().body("Emparejamiento inválido en la videollamada.");
        }

        int idReporta = dto.getIdProfile();
        int idSolicitante = empairing.getRequest().getProfile().getIdProfile();
        int idVoluntario = empairing.getProfile().getIdProfile();

        int idReported;
        if (idReporta == idSolicitante) {
            idReported = idVoluntario;
        } else if (idReporta == idVoluntario) {
            idReported = idSolicitante;
        } else {
            return ResponseEntity.badRequest().body("El perfil no participó en esta videollamada.");
        }

        Reports report = new Reports();
        report.setVideocall(videocall);
        report.setTypeReport(typeReportOpt.get());
        report.setStateReport(stateReportOpt.get());
        report.setDateReport(LocalDateTime.now(ZoneId.of("America/Lima")));
        report.setDescriptionReport(dto.getDescriptionReport());
        report.setIdReported(idReported);

        rS.insert(report);

        return ResponseEntity.status(HttpStatus.CREATED).body("Reporte registrado correctamente.");
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        rS.delete(id);
    }
    @GetMapping("/{id}")
    public ReportsDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        ReportsDTO dto=m.map(rS.listId(id),ReportsDTO.class);
        return dto;
    }
    @GetMapping
    public List<ReportsDTO> list(){
        return rS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,ReportsDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody ReportsDTO dto) {
        ModelMapper m = new ModelMapper();
        Reports a=m.map(dto,Reports.class);
        rS.insert(a);
    }
}
