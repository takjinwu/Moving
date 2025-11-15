package com.example.moving.chw

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moving.api.kakao.KakaoApiService
import com.example.moving.api.kakao.KakaoResponse
import com.example.moving.api.kakao.KakaoRetrofitClient
import com.example.moving.databinding.FragmentCinemaListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CinemaListFragment : Fragment() {

    private var _binding: FragmentCinemaListBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var adapter: CinemaAdapter
    private val cinemaList = mutableListOf<com.example.moving.api.kakao.KakaoPlace>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCinemaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        adapter = CinemaAdapter(cinemaList)
        binding.recyclerCinema.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCinema.adapter = adapter

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                loadCinemas(location.latitude, location.longitude)
            } else {
                // 테스트용 서울시청 좌표
                loadCinemas(37.5665, 126.9780)
            }
        }
    }

    private fun loadCinemas(lat: Double, lon: Double) {
        val api = KakaoRetrofitClient.instance.create(KakaoApiService::class.java)
        val apiKey = "KakaoAK 45d334129763d1f703fd122794673be7"

        api.getCinemas(apiKey, "영화관", lon, lat, 10000)
            .enqueue(object : Callback<KakaoResponse> {
                override fun onResponse(call: Call<KakaoResponse>, response: Response<KakaoResponse>) {
                    if (response.isSuccessful) {
                        val list = response.body()?.documents ?: emptyList()
                        cinemaList.clear()
                        cinemaList.addAll(list)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<KakaoResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
