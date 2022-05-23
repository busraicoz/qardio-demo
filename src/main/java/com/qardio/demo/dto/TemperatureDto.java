package com.qardio.demo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "temperature")
@Data
public class TemperatureDto {
    @Id
    private String id = UUID.randomUUID().toString();
    @NotBlank
    private String deviceId;
    @NotNull
    private Double tempDegree;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime creationDate;

    public TemperatureDto() {

    }
    public TemperatureDto(String deviceId, Double tempDegree, LocalDateTime date) {
        this.deviceId = deviceId;
        this.tempDegree = tempDegree;
        this.creationDate = date;
    }

}
