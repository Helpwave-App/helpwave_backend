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
        if (dto.getNewTokenDevice() == null || dto.getIdUser() == null) {
            throw new RuntimeException("Parámetros insuficientes para crear o actualizar el dispositivo.");
        }

        Optional<Device> existingByOldToken = Optional.empty();

        if (dto.getOldTokenDevice() != null) {
            existingByOldToken = dR.findByTokenDevice(dto.getOldTokenDevice());
        }

        if (existingByOldToken.isPresent()) {
            // Caso: actualizar token existente con uno nuevo
            Device device = existingByOldToken.get();
            device.setTokenDevice(dto.getNewTokenDevice());
            device.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Lima")));

            User user = new User();
            user.setIdUser(dto.getIdUser());
            device.setUser(user);

            dR.save(device);
            return;
        }

        Optional<Device> existingByNewToken = dR.findByTokenDevice(dto.getNewTokenDevice());

        if (existingByNewToken.isPresent()) {
            // Reasociar token existente al nuevo usuario
            Device device = existingByNewToken.get();

            User user = new User();
            user.setIdUser(dto.getIdUser());
            device.setUser(user);
            device.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Lima")));

            dR.save(device);
        } else {
            // Crear nuevo dispositivo
            Device newDevice = new Device();
            newDevice.setTokenDevice(dto.getNewTokenDevice());
            newDevice.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Lima")));

            User user = new User();
            user.setIdUser(dto.getIdUser());
            newDevice.setUser(user);

            dR.save(newDevice);
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
