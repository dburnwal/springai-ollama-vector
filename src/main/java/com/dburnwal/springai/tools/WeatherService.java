
package com.dburnwal.springai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class WeatherService {

    // Request classes for function calling
    public record WeatherRequest(String location) {}

    public record ForecastRequest(String location, int days) {}


    private final Random random = new Random();

    /**
     * Get the current weather for a specific location
     * 
     * @param location The city name
     * @return Current weather information including temperature and conditions
     */
    @Tool(name = "weather", description = "Get the current weather for a specific location")
    public Map<String, Object> getCurrentWeather(String location) {
        Map<String, Object> weather = new HashMap<>();
        weather.put("location", location);
        weather.put("temperature", random.nextInt(30) + 5); // Random temp between 5-35°C
        weather.put("condition", getRandomCondition());
        weather.put("humidity", random.nextInt(50) + 30); // Random humidity between 30-80%
        return weather;
    }

    /**
     * Get weather forecast for the next few days
     * 
     * @param location The city name
     * @param days Number of days to forecast (1-7)
     * @return Weather forecast for the specified number of days
     */
    @Tool(name = "forecast", description = "Get weather forecast for the next few days")
    public Map<String, Object> getWeatherForecast(String location, int days) {
        Map<String, Object> forecast = new HashMap<>();
        forecast.put("location", location);
        forecast.put("days", Math.min(days, 7));

        Map<String, Object> dailyForecast = new HashMap<>();
        for (int i = 1; i <= Math.min(days, 7); i++) {
            Map<String, Object> dayWeather = new HashMap<>();
            dayWeather.put("temperature", random.nextInt(30) + 5);
            dayWeather.put("condition", getRandomCondition());
            dailyForecast.put("Day " + i, dayWeather);
        }
        forecast.put("forecast", dailyForecast);

        return forecast;
    }


    private String getRandomCondition() {
        String[] conditions = {"Sunny", "Cloudy", "Rainy", "Partly Cloudy", "Windy"};
        return conditions[random.nextInt(conditions.length)];
    }
}
