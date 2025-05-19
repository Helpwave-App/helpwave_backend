package upc.helpwave.serviceimplements;

import io.agora.media.RtcTokenBuilder2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Videocall;
import upc.helpwave.repositories.VideocallRepository;
import upc.helpwave.serviceinterfaces.IVideocallService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideocallServiceImplement implements IVideocallService {
    @Autowired
    private VideocallRepository vR;

    @Value("${agora.app-id}")
    private String appId;

    @Value("${agora.app-certificate}")
    private String appCertificate;

    @Override
    public void insert(Videocall videocall) {
        vR.save(videocall);
    }

    @Override
    public void delete(Integer idVideocall) {
        vR.deleteById(idVideocall);
    }

    @Override
    public Videocall listId(Integer idVideocall) {
        return vR.findById(idVideocall).orElse(new Videocall());
    }

    @Override
    public List<Videocall> list() {
        return vR.findAll();
    }

    public Videocall createVideocall(Empairing empairing) {
        String channelName = "call_" + empairing.getIdEmpairing() + "_" + UUID.randomUUID();
        int uid = empairing.getRequest().getIdRequest();

        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        String token = tokenBuilder.buildTokenWithUid(
                appId,
                appCertificate,
                channelName,
                uid,
                RtcTokenBuilder2.Role.ROLE_PUBLISHER,
                3600,
                3600
        );

        Videocall videocall = new Videocall();
        videocall.setEmpairing(empairing);
        videocall.setChannel(channelName);
        videocall.setToken(token);
        videocall.setStartVideocall(LocalDateTime.now(ZoneId.of("America/Lima")));
        videocall.setEndVideocall(LocalDateTime.now(ZoneId.of("America/Lima")).plusHours(1));

        return vR.save(videocall);
    }
    @Override
    public Optional<Videocall> findByEmpairingId(Integer idEmpairing) {
        Empairing empairing = new Empairing();
        empairing.setIdEmpairing(idEmpairing);
        return vR.findByEmpairing(empairing);
    }
}
