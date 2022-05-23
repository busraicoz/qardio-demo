package com.qardio.demo.controller;

import com.qardio.demo.constant.AggregationType;
import com.qardio.demo.model.request.TemperatureRequest;
import com.qardio.demo.model.response.TemperatureCreateResponse;
import com.qardio.demo.model.response.TemperatureResponse;
import com.qardio.demo.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/temperature")
public class TemperatureController {
    @Autowired
    TemperatureService temperatureService;
    private final Logger logger = LoggerFactory.getLogger(TemperatureController.class);

    @PostMapping("/save")
    public TemperatureCreateResponse saveTemperatures(@RequestBody @Valid TemperatureRequest request) {
        logger.info("Service Called for Temperature Saving");
        temperatureService.save(request);
        return new TemperatureCreateResponse(LocalDateTime.now(),HttpStatus.CREATED);
    }

    @PostMapping("/bulk-save")
    public TemperatureCreateResponse saveTemperatures(@RequestBody @Valid List<TemperatureRequest> request) {
        logger.info("Service Called for Bulk Temperature Saving");
        temperatureService.saveAll(request);
        return new TemperatureCreateResponse(LocalDateTime.now(),HttpStatus.CREATED);
    }

    @GetMapping("/hourly")
    @ResponseBody
    public TemperatureResponse aggregateHourly() {
        logger.info("Service Called for Hourly Aggregate Report");
        return temperatureService.getAggregateResponse(AggregationType.HOURLY);
    }

    @GetMapping("/daily")
    @ResponseBody
    public TemperatureResponse aggregateDaily() {
        logger.info("Service Called for Daily Aggregate Report");
        return temperatureService.getAggregateResponse(AggregationType.DAILY);
    }

}
