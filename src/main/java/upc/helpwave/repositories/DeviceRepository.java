package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>{
}
