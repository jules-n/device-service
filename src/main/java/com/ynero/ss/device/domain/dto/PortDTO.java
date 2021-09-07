package com.ynero.ss.device.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortDTO {
    private String name;
    private Object value;
    private List<String> pipelinesId;
}
