package com.ynero.ss.device.domain;

import com.mongodb.lang.Nullable;
import domain.SendingData;
import domain.SendingParameters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Port {
    private String name;
    private Object value;
    private LocalDateTime lastUpdate;
    private List<UUID> pipelinesId;
    @Nullable
    private SendingParameters sendingParameters;
}
