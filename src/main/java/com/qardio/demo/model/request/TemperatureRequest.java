package com.qardio.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TemperatureRequest {
    @NotBlank(message = "deviceId is mandatory")
    private String deviceId;
    @NotNull(message = "tempDegree is mandatory")
    private Double tempDegree;
    @NotNull(message = "date is mandatory")
    private LocalDateTime date;
}
