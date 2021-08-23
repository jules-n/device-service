package com.ynero.ss.device.persistence;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class PortRepositoryCustomImpl implements PortRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean updateSnapshot(Port port, UUID deviceId) {
        Update update = new Update();
        update
                .set("ports.$.value", port.getValue())
                .set("ports.$.lastUpdate", port.getLastUpdate());

        var result = mongoTemplate.updateFirst(
                new Query(where("id").is(deviceId)
                        .and("ports.name").is(port.getName())),
                update,
                Device.COLLECTION_NAME
        );
        return result.wasAcknowledged();
    }

    @Override
    public Port findSnapshot(String portName, UUID deviceId) {
        var foundDevice = mongoTemplate.find(new Query(where("id").is(deviceId)
                .and("ports.name").is(portName)), Device.class).get(0);
        var foundPort = Arrays.stream(foundDevice.getPorts())
                .filter(port -> port.getName().equals(portName))
                .findFirst()
                .get();
        return foundPort;
    }

    @Override
    public Device[] getAllRelatedDevicesByPipelinesId(UUID pipelineId) {
        var deviceList = mongoTemplate.find(new Query(where("ports.pipelinesId").is(pipelineId)), Device.class);
        return deviceList.toArray(Device[]::new);
    }

    @SneakyThrows
    @Override
    public Port addPort(Port port, UUID deviceId) {
        Update update = new Update();
        update.addToSet("ports", port);
        var result = mongoTemplate.updateFirst(new Query(where("id").is(deviceId)), update, Device.class);
        if (result.wasAcknowledged())
            return port;
        throw new Exception("Port cant be added");
    }
}
