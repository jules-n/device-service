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
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class PortRepositoryCustomImpl implements PortRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    // TODO: rename, do not use word "snapshot", better name would be something like "updatePortValue"
    @Override
    public boolean updateSnapshot(Port port, UUID deviceId) {
        Update update = new Update();
        update
                .set("ports.$.value", port.getValue())
                .set("ports.$.lastUpdate", port.getLastUpdate());

        final Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").is(deviceId),
                Criteria.where("ports.name").is(port.getName()));

        var result = mongoTemplate.updateFirst(new Query(criteria),
                update,
                Device.class
        );
        return result.wasAcknowledged();
    }

    // TODO: rename, do not use word "snapshot", better name would be something like "findPort"
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
    public List<Device> getAllRelatedDevicesByPipelinesId(UUID pipelineId) {
        return mongoTemplate.find(new Query(where("ports.pipelinesId").is(pipelineId)), Device.class);
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

    public boolean addPipelineToPort(UUID pipelineId, String portName, UUID deviceId){
        Update update = new Update();
        update.addToSet("ports.$.pipelinesId", pipelineId);

        final Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").is(deviceId),
                Criteria.where("ports.name").is(portName));

        var result = mongoTemplate.updateFirst(new Query(criteria),
                update,
                Device.class
        );

        return result.wasAcknowledged();
    }
}
