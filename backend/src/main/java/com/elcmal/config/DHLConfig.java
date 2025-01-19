package com.elcmal.config;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Base64;

@Configuration
public class DHLConfig {

//    @Value("${dhl.api.baseUrl}")
//    private String baseUrl;
//
//    @Value("${dhl.api.key}")
//    private String apiKey;
//
//    @Value("${dhl.api.secret}")
//    private String apiSecret;

//    @Bean
//    public OkHttpClient dhlHttpClient() {
//        return new OkHttpClient.Builder()
//                .addInterceptor(chain -> {
//                    Request original = chain.request();
//                    Request request = original.newBuilder()
//                            .header("Authorization", "Basic " + apiKey + ":" + apiSecret)
//                            .method(original.method(), original.body())
//                            .build();
//                    return chain.proceed(request);
//                })
//                .build();
//    }
//@Bean
//public OkHttpClient dhlHttpClient() {
//    return new OkHttpClient.Builder()
//            .addInterceptor(chain -> {
//                Request original = chain.request();
//                String credentials = apiKey + ":" + apiSecret;
//                String encodedAuth = Base64.getEncoder().encodeToString(credentials.getBytes());
//
//                // Adding Authorization header with Base64 encoded credentials
//                Request request = original.newBuilder()
//                        .header("Authorization", "Basic " + encodedAuth)
//                        .method(original.method(), original.body())
//                        .build();
//                return chain.proceed(request);
//            })
//            .build();
//}
//
//
//    @Bean
//    public Retrofit dhlRetrofit(OkHttpClient dhlHttpClient) {
//
//        return new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .client(dhlHttpClient)
//                .addConverterFactory(JacksonConverterFactory.create())
//                .build();
//    }
}
