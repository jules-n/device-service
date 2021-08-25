package com.ynero.ss.device.domain;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = Device.COLLECTION_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    public static final String COLLECTION_NAME = "devices";
    @Indexed(unique = true)
    private UUID id;
    private String tenantId;
    private Port[] ports;
}
