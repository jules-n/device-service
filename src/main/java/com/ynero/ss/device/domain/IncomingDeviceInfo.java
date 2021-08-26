package com.ynero.ss.device.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomingDeviceInfo {
    private Device incomingDevice;
    private Port activePortOnIncomingDevice;
}
