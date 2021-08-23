package com.ynero.ss.device_service.persistence;

import com.ynero.ss.device_service.domain.Device;
import com.ynero.ss.device_service.domain.Port;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.UUID;

public class PortRepositoryCustomImpl implements PortRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean updateSnapshot(Port port, UUID deviceId) {
        Criteria deviceIdCriteria = new Criteria("id").is(deviceId);
        Criteria nameOfPort = new Criteria("ports.name").is(port.getName());
        Update update = new Update();
        update.set("ports.$.value", port.getValue()).set("ports.$.lastUpdate", port.getLastUpdate());
        var result = mongoTemplate.updateFirst(
                new Query(deviceIdCriteria.andOperator(nameOfPort)),
                update,
                Device.COLLECTION_NAME
        );
        return result.wasAcknowledged();
    }

    @Override
    public Port findSnapshot(Port port, UUID deviceId) {
        Criteria deviceIdCriteria = new Criteria("id").is(deviceId);
        /*Criteria nameOfPort = new Criteria("ports.name").is(port.getName());*/
        var device = mongoTemplate.find(new Query(deviceIdCriteria), Device.class).get(0);
        var foundPort = Arrays.stream(device.getPorts()).filter(_port -> port.getName().equals(_port)).findFirst();
        return foundPort.get();
    }

    @Override
    public Device[] getAllRelatedDevicesByPipelinesId(UUID pipelineId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("pipelinesId").is(pipelineId));
        var deviceList = mongoTemplate.find(query, Device.class);
        return deviceList.toArray(Device[]::new);
    }

    @SneakyThrows
    @Override
    public Port addPort(Port port, UUID deviceId) {
        Update update = new Update();
        update.addToSet("ports", port);
        Criteria criteria = Criteria.where("id").is(deviceId);
        var result = mongoTemplate.updateFirst(Query.query(criteria), update, Device.COLLECTION_NAME);
        if (result.wasAcknowledged())
            return port;
        throw new Exception("Port cant be added");
    }
}
