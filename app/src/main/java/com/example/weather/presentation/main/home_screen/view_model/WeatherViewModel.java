package com.example.weather.presentation.main.home_screen.view_model;


import android.os.Parcel;
import android.os.Parcelable;

public class WeatherViewModel implements Parcelable {
    private String condition;
    private String date;
    private int iconId;
    private String temperature;
    private String temperatureNight;
    private String windSpeed;
    private String humidity;
    private String pressure;

    public WeatherViewModel(String condition,
                            String date,
                            int iconId,
                            String temperature,
                            String temperatureNight,
                            String windSpeed,
                            String humidity,
                            String pressure) {
        this.condition = condition;
        this.date = date;
        this.iconId = iconId;
        this.temperature = temperature;
        this.temperatureNight = temperatureNight;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getCondition() {
        return condition;
    }

    public String getDate() {
        return date;
    }

    public int getIconId() {
        return iconId;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTemperatureNight() {
        return temperatureNight;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    protected WeatherViewModel(Parcel in) {
        condition = in.readString();
        date = in.readString();
        iconId = in.readInt();
        temperature = in.readString();
        temperatureNight = in.readString();
        windSpeed = in.readString();
        humidity = in.readString();
        pressure = in.readString();
    }

    public static final Creator<WeatherViewModel> CREATOR = new Creator<WeatherViewModel>() {
        @Override
        public WeatherViewModel createFromParcel(Parcel in) {
            return new WeatherViewModel(in);
        }

        @Override
        public WeatherViewModel[] newArray(int size) {
            return new WeatherViewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(condition);
        parcel.writeString(date);
        parcel.writeInt(iconId);
        parcel.writeString(temperature);
        parcel.writeString(temperatureNight);
        parcel.writeString(windSpeed);
        parcel.writeString(humidity);
        parcel.writeString(pressure);
    }
}
