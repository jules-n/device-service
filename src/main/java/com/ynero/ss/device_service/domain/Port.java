package com.ynero.ss.device_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Port {

    private String name;
    private Object value;
    private LocalDateTime lastUpdate;
    private UUID[] pipelinesId;
}
