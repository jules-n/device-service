package com.ynero.ss.device.config;

import lombok.Setter;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class AdminClientConfig {

    @Setter(onMethod_ = {@Autowired})
    KafkaAdmin kafkaAdmin;

    @Bean
    public AdminClient getAdminClient(){
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }
}
