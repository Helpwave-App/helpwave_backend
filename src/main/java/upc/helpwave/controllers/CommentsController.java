package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.CommentsDTO;
import upc.helpwave.entities.Comments;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Videocall;
import upc.helpwave.repositories.CommentsRepository;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.VideocallRepository;
import upc.helpwave.serviceinterfaces.ICommentsService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private ICommentsService cS;
    @Autowired
    private CommentsRepository cR;
    @Autowired
    private VideocallRepository vR;
    @Autowired
    private ProfileRepository pR;
    @PostMapping
    public ResponseEntity<String> registerComment(@RequestBody CommentsDTO dto) {
        Optional<Videocall> videocallOpt = vR.findById(dto.getIdVideocall());

        if (!videocallOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Videocall videocall = videocallOpt.get();

        Comments comment = new Comments();
        comment.setVideocall(videocall);
        comment.setDescriptionComment(dto.getDescriptionComment());
        comment.setScoreVolunteer(dto.getScoreVolunteer());
        comment.setScoreVideocall(dto.getScoreVideocall());
        comment.setDateComment(LocalDateTime.now(ZoneId.of("America/Lima")));

        cR.save(comment);

        Profile volunteer = videocall.getEmpairing().getProfile();
        Integer assistances = volunteer.getAssistances() != null ? volunteer.getAssistances() : 0;
        BigDecimal currentScore = volunteer.getScoreProfile() != null ? volunteer.getScoreProfile() : BigDecimal.ZERO;

        if (assistances > 0) {
            BigDecimal assistancesBD = BigDecimal.valueOf(assistances);
            BigDecimal newScoreBD = BigDecimal.valueOf(dto.getScoreVolunteer());

            BigDecimal sumatoria = currentScore.multiply(BigDecimal.valueOf(assistances - 1)).add(newScoreBD);
            BigDecimal promedio = sumatoria.divide(assistancesBD, 2, RoundingMode.HALF_UP);

            volunteer.setScoreProfile(promedio);
            pR.save(volunteer);
        }

        return ResponseEntity.ok("Comentario registrado y puntaje actualizado.");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        cS.delete(id);
    }

    @GetMapping("/{id}")
    public CommentsDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        CommentsDTO dto=m.map(cS.listId(id),CommentsDTO.class);
        return dto;
    }
    @GetMapping
    public List<CommentsDTO> list(){
        return cS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,CommentsDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody CommentsDTO dto) {
        ModelMapper m = new ModelMapper();
        Comments a=m.map(dto,Comments.class);
        cS.insert(a);
    }

}
