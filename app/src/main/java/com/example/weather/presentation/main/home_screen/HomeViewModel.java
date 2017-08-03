package com.example.weather.presentation.main.home_screen;


import com.example.weather.domain.entities.weather.DetailedWeather;
import com.example.weather.utils.TemperatureConverter;

public class HomeViewModel {

    public static HomeViewModel create(DetailedWeather detailedWeather) {
        return new HomeViewModel(detailedWeather.getMain().getTemp(),
                detailedWeather.getWind().getSpeed(),
                detailedWeather.getWeather().get(0).getMain(),
                detailedWeather.getMain().getPressure(),
                detailedWeather.getWeather().get(0).getIcon(),
                detailedWeather.getName());
    }

    private HomeViewModel(Double temperature, Double wind, String main, Double pressure, String iconId, String city) {
        this.temperature =  (TemperatureConverter.kelvinToCelsius(temperature) > 0 ? "+" : "") +
            String.valueOf(TemperatureConverter.kelvinToCelsius(temperature)) + "°";
        this.wind = "Wind " + String.valueOf(wind) + " m/s";
        this.main = main;
        this.pressure = String.valueOf(pressure);
        this.iconId = "http://openweathermap.org/img/w/" + iconId + ".png";
        this.city = city;
    }

    private String temperature;
    private String wind;
    private String main;
    private String pressure;
    private String iconId;
    private String city;

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWind() {
        return wind;
    }

    public String getMain() {
        return main;
    }

    public String getPressure() {
        return pressure;
    }

    public String getIconId() {
        return iconId;
    }

    @Override
    public String toString() {
        return "HomeViewModel{" +
                "temperature='" + temperature + '\'' +
                ", wind='" + wind + '\'' +
                ", main='" + main + '\'' +
                ", pressure='" + pressure + '\'' +
                ", iconId='" + iconId + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
