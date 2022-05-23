package com.qardio.demo.controller;

import com.qardio.demo.dto.TemperatureDto;
import com.qardio.demo.model.request.TemperatureRequest;
import com.qardio.demo.model.response.TemperatureResponse;
import com.qardio.demo.repository.TemperatureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TemperatureControllerTest {
    @Autowired
    TemperatureController temperatureController;
    @Autowired
    TemperatureRepository temperatureRepository;

    @Test
    public void testSaveOnlyOneRecord() {
        temperatureRepository.deleteAll();
        Assertions.assertEquals(0,temperatureRepository.count());
        TemperatureRequest request=new TemperatureRequest("xyz",23.0, LocalDateTime.now());
        temperatureController.saveTemperatures(request);
        Assertions.assertEquals(1,temperatureRepository.count());
    }
    @Test
    public void testSaveBulkRecords() {
        temperatureRepository.deleteAll();
        Assertions.assertEquals(0,temperatureRepository.count());
        List<TemperatureRequest> temperatures=new ArrayList<>();
        temperatures.add(new TemperatureRequest("xyz",23.0, LocalDateTime.now()));
        temperatures.add(new TemperatureRequest("200L",24.0, LocalDateTime.now()));
        temperatures.add(new TemperatureRequest("100L",25.0, LocalDateTime.now()));
        temperatureController.saveTemperatures(temperatures);
        Assertions.assertEquals(3,temperatureRepository.count());
    }

    @Test
    public void testAggregateResponseForDaily() {
        temperatureRepository.deleteAll();
        Assertions.assertEquals(0,temperatureRepository.count());
        List<TemperatureDto> temperatures=new ArrayList<>();
        temperatures.add(new TemperatureDto("xyz",23.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("200L",27.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("400L",24.0, LocalDateTime.now().minusDays(1)));
        temperatureRepository.saveAll(temperatures);
        Assertions.assertEquals(3, temperatureRepository.findAll().size());

        TemperatureResponse temperatureResponse=temperatureController.aggregateDaily();
        Assertions.assertEquals(2, temperatureResponse.getTempDataCount());
        Assertions.assertEquals(23.0,temperatureResponse.getMinTempDegree().doubleValue());
        Assertions.assertEquals(27.0,temperatureResponse.getMaxTempDegree().doubleValue());
        Assertions.assertEquals(25.0,temperatureResponse.getAverageTempDegree().doubleValue());
    }
    @Test
    public void testAggregateResponseForHourly() {
        temperatureRepository.deleteAll();
        Assertions.assertEquals(0,temperatureRepository.count());
        List<TemperatureDto> temperatures=new ArrayList<>();
        temperatures.add(new TemperatureDto("xyz",34.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("xyz",20.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("xyz",30.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("200L",24.0, LocalDateTime.now().minusHours(1)));
        temperatureRepository.saveAll(temperatures);
        Assertions.assertEquals(4, temperatureRepository.findAll().size());

        TemperatureResponse temperatureResponse=temperatureController.aggregateHourly();
        Assertions.assertEquals(3,temperatureResponse.getTempDataCount());
        Assertions.assertEquals(28.0,temperatureResponse.getAverageTempDegree().doubleValue());
        Assertions.assertEquals(34.0,temperatureResponse.getMaxTempDegree().doubleValue());
        Assertions.assertEquals(20.0, temperatureResponse.getMinTempDegree().doubleValue());
    }
}