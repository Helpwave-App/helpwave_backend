package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.Device;
import java.util.List;

public interface IDeviceService {
    public void insert(Device device);

    public void delete(Integer idDevice);

    public Device listId(Integer idDevice);

    public List<Device> list();
}
