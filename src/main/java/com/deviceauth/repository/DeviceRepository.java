package com.deviceauth.repository;
import com.deviceauth.entity.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DeviceRepository extends JpaRepository<Device,Long> {
 List<Device> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
 Optional<Device> findByIdAndOwnerId(Long id,Long ownerId);
 boolean existsByOwnerIdAndDeviceIdentifier(Long ownerId,String identifier);
}
