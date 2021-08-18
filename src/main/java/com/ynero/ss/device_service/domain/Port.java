package com.ynero.ss.device_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Port {

    private String name;
    private Object value;
    private UUID[] pipelinesId;
}
