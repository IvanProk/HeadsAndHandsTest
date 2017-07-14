package com.ivan.prokofyev.handh_test.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shiraha on 7/13/2017.
 */
@AutoValue
public abstract class WeatherResponse {

    @SerializedName("weather")
    public abstract List<WeatherDescription> weather();

    @SerializedName("main")
    public abstract Main main();

    public static TypeAdapter<WeatherResponse> typeAdapter(Gson gson){
        return new AutoValue_WeatherResponse.GsonTypeAdapter(gson);
    }

    @AutoValue
    public abstract static class WeatherDescription {

        @SerializedName("description")
        public abstract String description();


        public static TypeAdapter<WeatherResponse.WeatherDescription> typeAdapter(Gson gson){
            return new AutoValue_WeatherResponse_WeatherDescription.GsonTypeAdapter(gson);
        }
//        @AutoValue.Builder
//        public abstract static class Builder{
//            public abstract Builder setDescription();
//            public abstract WeatherDescription build();
//        }
    }

    @AutoValue
    public abstract static class Main {
        @SerializedName("temp")
        public abstract int temp();

        public static TypeAdapter<WeatherResponse.Main> typeAdapter(Gson gson){
            return new AutoValue_WeatherResponse_Main.GsonTypeAdapter(gson);
        }
//        @AutoValue.Builder
//        public abstract static class Builder{
//            public abstract Builder setTemp();
//            public abstract Main build();
//        }
    }
//    @AutoValue.Builder
//    public abstract static class Builder{
//        public abstract WeatherResponse build();
//    }
}
