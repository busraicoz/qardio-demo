package com.qardio.demo.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TemperatureResponse {
  private int tempDataCount;
  private Double averageTempDegree;
  private Double minTempDegree;
  private Double maxTempDegree;
}
