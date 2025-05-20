package upc.helpwave.serviceinterfaces;

import upc.helpwave.dtos.DeviceUpsertDTO;
import upc.helpwave.entities.Device;
import java.util.List;

public interface IDeviceService {
    public void insert(Device device);

    public void delete(String tokenDevice);

    public Device listId(Integer idDevice);

    public List<Device> list();

    public void upsert(DeviceUpsertDTO dto);
}
