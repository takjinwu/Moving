package com.example.moving

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController // ⭐️ [추가] Navigation Component 사용을 위한 import
import com.example.moving.databinding.FragmentSettingBinding // 가정: SettingFragment의 바인딩 클래스

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // fragment_setting.xml 레이아웃 인플레이트
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ⭐️ [핵심 로직 1] Fragment가 나타날 때 Toolbar 숨기기
        (activity as? MainActivity)?.binding?.appBarMain?.toolbar?.visibility = View.GONE

        // ⭐️ [추가된 로직] arrow 이미지(imageView12) 클릭 리스너 설정
        binding.imageView12.setOnClickListener {
            // Navigation Component를 사용하여 이전 목적지로 이동 (뒤로 가기)
            findNavController().navigateUp()
        }

        // 여기에 다른 UI 로직 (닉네임 변경 등)을 구현합니다.
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // ⭐️ [핵심 로직 2] Fragment가 화면에서 사라질 때 Toolbar 다시 보이게 하기
        (activity as? MainActivity)?.binding?.appBarMain?.toolbar?.visibility = View.VISIBLE

        // 메모리 누수 방지를 위해 바인딩 해제
        _binding = null
    }
}