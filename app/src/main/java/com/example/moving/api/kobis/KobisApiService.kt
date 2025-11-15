package com.example.moving.api.kobis

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisApiService {

    // 일별 박스오피스 API
    @GET("boxoffice/searchDailyBoxOfficeList.json")
    fun getDailyBoxOffice(
        @Query("key") apiKey: String,
        @Query("targetDt") date: String
    ): Call<KobisResponse>
}