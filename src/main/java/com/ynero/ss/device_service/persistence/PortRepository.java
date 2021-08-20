package com.ynero.ss.device_service.persistence;

import com.ynero.ss.device_service.domain.Device;
import com.ynero.ss.device_service.domain.Port;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PortRepository extends MongoRepository<Port, UUID> {
    boolean updatePortValue(Port port, UUID deviceId);
    Device[] getAllRelatedDevicesByPortsPipelineId(UUID pipelineId);
    Port insertPort(Port port, UUID deviceId);
}
