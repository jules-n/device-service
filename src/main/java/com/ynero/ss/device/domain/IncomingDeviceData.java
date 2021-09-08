package com.ynero.ss.device.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomingDeviceData {
    private Device device;
    private Port activePort;
}
