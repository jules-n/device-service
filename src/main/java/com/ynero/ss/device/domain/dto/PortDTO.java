package com.ynero.ss.device.domain.dto;

import java.util.List;

public record PortDTO(String name, Object value, List<String> pipelinesId) {
}
