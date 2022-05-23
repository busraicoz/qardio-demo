package com.qardio.demo.model.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TemperatureCreateResponse {
  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();
  private HttpStatus response;
}
