package com.qardio.demo.service;

import com.qardio.demo.constant.AggregationType;
import com.qardio.demo.dto.TemperatureDto;
import com.qardio.demo.exception.DataNotFoundException;
import com.qardio.demo.model.request.TemperatureRequest;
import com.qardio.demo.model.response.TemperatureResponse;
import com.qardio.demo.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class TemperatureService {
    @Autowired
    TemperatureRepository temperatureRepository;

    public void save(TemperatureRequest request){
        TemperatureDto temperatureDto =new TemperatureDto(request.getDeviceId(),request.getTempDegree(),request.getDate());
        temperatureRepository.save(temperatureDto);
    }

    public void saveAll(List<TemperatureRequest> tempList){
        List<TemperatureDto> temperatures =new ArrayList<>();
        for (TemperatureRequest request:tempList){
            temperatures.add(new TemperatureDto(request.getDeviceId(),request.getTempDegree(),request.getDate()));
        }
        temperatureRepository.saveAll(temperatures);
    }

    public TemperatureResponse getAggregateResponse(AggregationType aggregationType){
        List<TemperatureDto> temperatureDtoList;
        if (aggregationType.equals(AggregationType.DAILY)){
            temperatureDtoList =selectDailyTempData();
        }else {
            temperatureDtoList =selectHourlyTempData();
        }
        return new TemperatureResponse(temperatureDtoList.size(),getAverageDegree(temperatureDtoList),getMinDegree(temperatureDtoList),getMaxDegree(temperatureDtoList));
    }
    protected List<TemperatureDto> selectHourlyTempData(){
        LocalDateTime aggregationTime=LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        return getTempListForCustomDate(aggregationTime);
    }
    protected List<TemperatureDto> selectDailyTempData(){
        LocalDateTime aggregationTime=LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return getTempListForCustomDate(aggregationTime);
    }

    protected Double getMinDegree(List<TemperatureDto> temperatureDtoList){
        OptionalDouble minDegree= OptionalDouble.of(temperatureDtoList.stream().min(Comparator.comparing(TemperatureDto::getTempDegree)).get().getTempDegree());
        return minDegree.isPresent()?minDegree.getAsDouble():null;
    }
    protected Double getMaxDegree(List<TemperatureDto> temperatureDtoList){
        OptionalDouble maxDegree= OptionalDouble.of(temperatureDtoList.stream().max(Comparator.comparing(TemperatureDto::getTempDegree)).get().getTempDegree());
        return maxDegree.isPresent()?maxDegree.getAsDouble():null;
    }
    protected List<TemperatureDto> getTempListForCustomDate(LocalDateTime localDateTime){
        List<TemperatureDto> tempList=temperatureRepository.findAll();
        if(tempList.size()<1){
            throw new DataNotFoundException();
        }
        return tempList.stream().filter(t->t.getCreationDate().isAfter(localDateTime) || t.getCreationDate().isEqual(localDateTime)).collect(Collectors.toList());
    }
    protected Double getAverageDegree(List<TemperatureDto> temperatureDtoList){
        OptionalDouble averageDegree = temperatureDtoList
                .stream()
                .mapToDouble(TemperatureDto::getTempDegree)
                .average();
        return averageDegree.isPresent() ? averageDegree.getAsDouble() : null;
    }

}
