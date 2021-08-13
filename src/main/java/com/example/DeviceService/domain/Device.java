package com.example.DeviceService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = Device.COLLECTION_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    public static final String COLLECTION_NAME = "devices";
    @Indexed(unique = true)
    private String serialNumber;
    private String tenantId;
    private Port[] ports;
}
