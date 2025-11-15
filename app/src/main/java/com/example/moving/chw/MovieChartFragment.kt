package com.example.moving.chw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moving.api.kobis.KobisApiService
import com.example.moving.api.kobis.KobisResponse
import com.example.moving.api.kobis.MovieBoxOffice
import com.example.moving.api.kobis.KobisRetrofitClient
import com.example.moving.databinding.FragmentMovieChartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class MovieChartFragment : Fragment() {
    private var _binding: FragmentMovieChartBinding? = null
    private val binding get() = _binding!!
    private val movieList = mutableListOf<MovieBoxOffice>()
    private lateinit var adapter: MovieChartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieChartAdapter(movieList)
        binding.recyclerMovieChart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMovieChart.adapter = adapter

        loadMovieChart()
    }

    private fun loadMovieChart() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerMovieChart.visibility = View.GONE

        val api = KobisRetrofitClient.instance.create(KobisApiService::class.java)
        val apiKey = "6a029d60c7390cd4395eac8d5577135f"
        val yesterday = SimpleDateFormat("20251114", Locale.KOREA)
            .format(System.currentTimeMillis() - 24 * 60 * 60 * 1000)

        api.getDailyBoxOffice(apiKey, yesterday)
            .enqueue(object : Callback<KobisResponse> {
                override fun onResponse(
                    call: Call<KobisResponse>,
                    response: Response<KobisResponse>
                ) {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerMovieChart.visibility = View.VISIBLE

                    if (response.isSuccessful) {
                        val list = response.body()?.boxOfficeResult?.dailyBoxOfficeList ?: return
                        movieList.clear()
                        movieList.addAll(list)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<KobisResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    t.printStackTrace()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}