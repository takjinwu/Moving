package com.example.moving.api.kakao

data class KakaoResponse(
    val documents: List<KakaoPlace>
)

data class KakaoPlace(
    val place_name: String,      // 영화관 이름
    val address_name: String,    // 주소
    val distance: String? = null // 거리 (미터 단위)
)
