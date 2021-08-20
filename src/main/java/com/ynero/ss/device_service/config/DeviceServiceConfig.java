package com.ynero.ss.device_service.config;

import com.ynero.ss.device_service.persistence.DeviceMongoService;
import com.ynero.ss.device_service.persistence.DeviceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceServiceConfig {

    @Bean
    public DeviceService getDeviceService(){
        return new DeviceMongoService();
    }
}
