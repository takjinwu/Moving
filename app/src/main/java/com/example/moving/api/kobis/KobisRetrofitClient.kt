package com.example.moving.api.kobis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KobisRetrofitClient {
    private const val BASE_URL = "https://kobis.or.kr/kobisopenapi/webservice/rest/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON을 Kotlin 객체로 변환
            .build()
    }
}