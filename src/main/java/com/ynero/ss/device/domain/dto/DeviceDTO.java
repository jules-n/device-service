package com.ynero.ss.device.domain.dto;
import java.util.List;

public record DeviceDTO(String id, String tenantId, List<PortDTO> ports) {
}
