package com.example.DeviceService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Port {

    private String name;
    private Object value;
    private String[] pipelinesId;
}
