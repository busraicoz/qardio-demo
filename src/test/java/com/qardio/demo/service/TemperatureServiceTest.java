package com.qardio.demo.service;

import com.qardio.demo.dto.TemperatureDto;
import com.qardio.demo.model.request.TemperatureRequest;
import com.qardio.demo.repository.TemperatureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TemperatureServiceTest {
    @Autowired
    TemperatureService temperatureService;
    @Autowired
    TemperatureRepository temperatureRepository;

    @Test
    public void testGetMinDegree() {
        List<TemperatureDto> temperatures=new ArrayList<>();
        temperatures.add(new TemperatureDto("xyz",23.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("200L",24.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("100L",25.0, LocalDateTime.now()));

        Assertions.assertEquals(23.0, temperatureService.getMinDegree(temperatures).doubleValue());
    }
    @Test
    public void testGetMaxDegree() {
        List<TemperatureDto> temperatures=new ArrayList<>();
        temperatures.add(new TemperatureDto("xyz",23.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("200L",40.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("100L",42.0, LocalDateTime.now()));

        Assertions.assertEquals(42.0,temperatureService.getMaxDegree(temperatures).doubleValue());
    }
    @Test
    public void testGetAverageDegree() {
        List<TemperatureDto> temperatures = new ArrayList<>();
        temperatures.add(new TemperatureDto("xyz", 26.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("200L", 24.0, LocalDateTime.now()));
        temperatures.add(new TemperatureDto("100L", 28.0, LocalDateTime.now()));

        Assertions.assertEquals(26.0, temperatureService.getAverageDegree(temperatures).doubleValue());

    }
    @Test
    public void testGetTempListForCustomDateForDailyRecord() {
        temperatureRepository.deleteAll();
        Assertions.assertEquals(0,temperatureRepository.count());
        List<TemperatureRequest> temperatures=new ArrayList<>();
        temperatures.add(new TemperatureRequest("xyz",23.0, LocalDateTime.now()));
        temperatures.add(new TemperatureRequest("200L",27.0, LocalDateTime.now()));
        temperatures.add(new TemperatureRequest("400L",24.0, LocalDateTime.now().minusHours(1)));
        temperatures.add(new TemperatureRequest("400L",24.0, LocalDateTime.now().minusHours(2)));
        temperatures.add(new TemperatureRequest("300L",24.0, LocalDateTime.now().minusDays(1)));
        temperatureService.saveAll(temperatures);
        Assertions.assertEquals(5, temperatureRepository.findAll().size());
        List<TemperatureDto> temperatureList=temperatureService.selectDailyTempData();
        Assertions.assertEquals(4, temperatureList.size());
    }
    @Test
    public void testGetTempListForCustomDateForHourlyRecord() {
        temperatureRepository.deleteAll();
        Assertions.assertEquals(0,temperatureRepository.count());
        List<TemperatureRequest> temperatures=new ArrayList<>();
        temperatures.add(new TemperatureRequest("xyz",23.0, LocalDateTime.now()));
        temperatures.add(new TemperatureRequest("200L",27.0, LocalDateTime.now()));
        temperatures.add(new TemperatureRequest("400L",24.0, LocalDateTime.now().minusHours(1)));
        temperatures.add(new TemperatureRequest("400L",24.0, LocalDateTime.now().minusHours(2)));
        temperatures.add(new TemperatureRequest("300L",24.0, LocalDateTime.now().minusDays(1)));
        temperatureService.saveAll(temperatures);
        Assertions.assertEquals(5, temperatureRepository.findAll().size());
        List<TemperatureDto> temperatureList=temperatureService.selectHourlyTempData();
        Assertions.assertEquals(2, temperatureList.size());
    }
}