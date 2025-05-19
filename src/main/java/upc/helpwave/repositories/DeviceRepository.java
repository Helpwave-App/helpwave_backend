package upc.helpwave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.helpwave.entities.Device;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>{
    void deleteByTokenDevice(String tokenDevice);
    Optional<Device> findByTokenDevice(String tokenDevice);
}
