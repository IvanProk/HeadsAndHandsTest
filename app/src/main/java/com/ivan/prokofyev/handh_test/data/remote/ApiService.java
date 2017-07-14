package com.ivan.prokofyev.handh_test.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivan.prokofyev.handh_test.data.model.WeatherResponse;
import com.ivan.prokofyev.handh_test.util.MyGsonTypeAdapterFactory;

import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    String ENDPOINT = "http://api.openweathermap.org/";
    String API_KEY = "9eecbfd4981acec867727144357c92bf";
    String ST_PETERSBURG_ID = "498817";
    String LOCALE = Locale.getDefault().getLanguage();
    String UNITS = "metric";


    @GET("data/2.5/weather?id="+ ST_PETERSBURG_ID +"&appid=" + API_KEY + "&units=" + UNITS)
    Observable<WeatherResponse> getWeather(@Query("lang") String lang);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static ApiService newWeatherService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(ApiService.class);
        }
    }
}
