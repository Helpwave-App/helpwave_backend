package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.dtos.DeviceUpsertDTO;
import upc.helpwave.entities.Device;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.DeviceRepository;
import upc.helpwave.serviceinterfaces.IDeviceService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImplement implements IDeviceService {
    @Autowired
    private DeviceRepository dR;

    @Override
    public void upsert(DeviceUpsertDTO dto) {
        if (dto.getOldTokenDevice() != null && dto.getNewTokenDevice() != null) {
            Optional<Device> existing = dR.findByTokenDevice(dto.getOldTokenDevice());
            if (existing.isPresent()) {
                Device device = existing.get();
                device.setTokenDevice(dto.getNewTokenDevice());
                device.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Lima")));
                dR.save(device);
            } else {
                throw new RuntimeException("No se encontró el dispositivo con el token: " + dto.getOldTokenDevice());
            }
        } else if (dto.getIdUser() != null && dto.getTokenDevice() != null) {
            Device newDevice = new Device();
            newDevice.setTokenDevice(dto.getTokenDevice());
            newDevice.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Lima")));

            User user = new User();
            user.setIdUser(dto.getIdUser());
            newDevice.setUser(user);

            dR.save(newDevice);
        } else {
            throw new RuntimeException("Parámetros insuficientes para crear o actualizar el dispositivo.");
        }
    }

    @Override
    public void insert(Device device) {
        dR.save(device);
    }

    @Override
    public void delete(String tokenDevice) {
        Optional<Device> deviceOpt = dR.findByTokenDevice(tokenDevice);
        if (deviceOpt.isPresent()) {
            dR.delete(deviceOpt.get());
            System.out.println("Dispositivo eliminado con token: " + tokenDevice);
        } else {
            System.out.println("No se encontró el token: " + tokenDevice);
        }
    }


    @Override
    public Device listId(Integer idDevice) {
        return dR.findById(idDevice).orElse(new Device());
    }

    @Override
    public List<Device> list() {
        return dR.findAll();
    }
}
