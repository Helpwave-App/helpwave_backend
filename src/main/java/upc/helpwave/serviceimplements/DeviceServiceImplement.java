package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Device;
import upc.helpwave.repositories.DeviceRepository;
import upc.helpwave.serviceinterfaces.IDeviceService;

import java.util.List;

@Service
public class DeviceServiceImplement implements IDeviceService {
    @Autowired
    private DeviceRepository dR;

    @Override
    public void insert(Device device) {
        dR.save(device);
    }

    @Override
    public void delete(Integer idDevice) {
        dR.deleteById(idDevice);
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
