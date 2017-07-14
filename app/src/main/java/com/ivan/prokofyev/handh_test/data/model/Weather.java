package com.ivan.prokofyev.handh_test.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Shiraha on 7/13/2017.
 */
@AutoValue
public abstract class Weather implements Parcelable {

    public abstract String description();

    public abstract int temperature();


    public static Weather create(WeatherResponse response) {
        return new AutoValue_Weather(response.weather().get(0).description(),
                response.main().temp());
    }

    public static Weather create(Weather weather) {
        return new AutoValue_Weather(weather.description(), weather.temperature());
    }

    public static Builder builder() {
        return new AutoValue_Weather.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setDescription(String description);

        public abstract Builder setTemperature(int temperature);

        public abstract Weather build();
    }
}
