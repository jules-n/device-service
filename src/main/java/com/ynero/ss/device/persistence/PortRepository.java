package com.ynero.ss.device.persistence;

import com.ynero.ss.device.domain.Port;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PortRepository extends MongoRepository<Port, UUID>, PortRepositoryCustom {
}
