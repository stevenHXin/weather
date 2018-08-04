package com.steven.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.weather.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class WeatherDataServiceImpl implements WeatherDataService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String WEATHER_API = "http://wthrcdn.etouch.cn/weather_mini";

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        StringBuilder sb = new StringBuilder();
        sb.append(WEATHER_API).append("?citykey=").append(cityId);
        return this.getWeatherData(sb.toString());
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        StringBuilder sb = new StringBuilder();
        sb.append(WEATHER_API).append("?city=").append(cityName);
        return this.getWeatherData(sb.toString());
    }

    private WeatherResponse getWeatherData(String uri) {
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String body = null;
        if (response.getStatusCodeValue() == 200) {
            body = response.getBody();
        }
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weather = null;
        try {
            weather = mapper.readValue(body, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
