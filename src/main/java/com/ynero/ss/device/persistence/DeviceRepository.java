package com.ynero.ss.device.persistence;

import com.ynero.ss.device.domain.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends MongoRepository<Device, UUID>{
    Optional<Device> findById(UUID id);
}
