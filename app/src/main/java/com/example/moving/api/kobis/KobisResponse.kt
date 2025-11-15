package com.example.moving.api.kobis

data class KobisResponse(
    val boxOfficeResult: BoxOfficeResult
)

data class BoxOfficeResult(
    val dailyBoxOfficeList: List<MovieBoxOffice>
)

data class MovieBoxOffice(
    val rank: String,
    val movieNm: String,
    val openDt: String,
    val audiCnt: String,
    val audiAcc: String
)
