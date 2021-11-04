package com.ynero.ss.device.config;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.domain.dto.DeviceDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(dtoDeviceToDevice);
        return modelMapper;
    }

    private Converter<DeviceDTO, Device> dtoDeviceToDevice = new AbstractConverter<>() {
        protected Device convert(DeviceDTO dto) {
            var ports = dto.ports().stream()
                    .map(
                            portDTO -> {
                                var pipelinesId = portDTO.pipelinesId().stream()
                                        .map( id -> UUID.fromString(id))
                                        .collect(Collectors.toList());

                                var port = Port.builder()
                                        .name(portDTO.name())
                                        .lastUpdate(LocalDateTime.now())
                                        .value(portDTO.value())
                                        .pipelinesId(pipelinesId)
                                        .build();

                                return port;
                            }
                    ).collect(Collectors.toList());

            var device = Device.builder()
                    .id(UUID.fromString(dto.id()))
                    .tenantId(dto.tenantId())
                    .ports(ports)
                    .build();
            return device;
        }
    };
}