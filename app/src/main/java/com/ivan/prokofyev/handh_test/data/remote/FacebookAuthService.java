package com.ivan.prokofyev.handh_test.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivan.prokofyev.handh_test.data.model.FacebookUserResponse;
import com.ivan.prokofyev.handh_test.util.MyGsonTypeAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Shiraha on 7/21/2017.
 */

public interface FacebookAuthService {

    String ENDPOINT = "https://graph.facebook.com/v2.5/";

    @GET("me?fields=first_name,email")
    Observable<FacebookUserResponse> getUserProfile(@Query("access_token") String token);

    /******** Helper class that sets up a new services *******/
    class Creator {
        public static FacebookAuthService newFacebookAuthService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(FacebookAuthService.class);
        }
    }
}
