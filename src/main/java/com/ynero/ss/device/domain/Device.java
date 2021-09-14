package com.ynero.ss.device.domain;

import com.mongodb.lang.Nullable;
import domain.SendingData;
import domain.SendingParameters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection = Device.COLLECTION_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    public static final String COLLECTION_NAME = "devices";
    @Indexed(unique = true, name = "deviceId")
    @Field(name = "id")
    private UUID id;
    private String tenantId;
    private List<Port> ports;
    @Nullable
    private SendingParameters sendingParameters;
}
