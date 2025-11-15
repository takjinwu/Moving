package com.example.moving.api.kakao

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {

    @GET("v2/local/search/keyword.json")
    fun getCinemas(
        @Header("Authorization") auth: String,  // KakaoAK {REST_API_KEY}
        @Query("query") query: String = "영화관",
        @Query("x") longitude: Double,
        @Query("y") latitude: Double,
        @Query("radius") radius: Int = 5000
    ): Call<KakaoResponse>
}
