package com.example.DeviceService.services.categorizer;

import com.google.pubsub.v1.PubsubMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class DeviceDataCategorizer {
    public void messageHandling(PubsubMessage pubsubMessage){
        log.info("message: {}",pubsubMessage);
    }
}
