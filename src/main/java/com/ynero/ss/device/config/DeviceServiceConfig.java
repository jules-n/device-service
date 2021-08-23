package com.ynero.ss.device.config;

import com.ynero.ss.device.persistence.DeviceServiceMongoImpl;
import com.ynero.ss.device.persistence.DeviceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceServiceConfig {

    @Bean
    public DeviceService getDeviceService(){
        return new DeviceServiceMongoImpl();
    }
}
