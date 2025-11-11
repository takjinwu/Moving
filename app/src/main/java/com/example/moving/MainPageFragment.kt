package com.example.moving

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.moving.databinding.FragmentMainPageBinding // 가정한 바인딩 클래스

// MainPageFragment의 새 인스턴스를 생성할 때 사용할 파라미터는 제거했습니다.
// 이 프래그먼트가 Navigation Component에 의해 사용된다고 가정합니다.

class MainPageFragment : Fragment() {

    // View Binding 변수 선언
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabs: List<TextView>
    private lateinit var indicators: List<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 레이아웃 인플레이트 및 바인딩 초기화
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. 탭과 인디케이터 뷰들을 리스트로 묶습니다.
        tabs = listOf(binding.textView9, binding.textView10, binding.textView11)
        indicators = listOf(binding.indicatorBar1, binding.indicatorBar2, binding.indicatorBar3)

        // 2. 초기 상태 설정 (Tab 1만 활성화)
        // XML에서 이미 Tab 1 (textView9)가 bold, indicator_bar_1이 visible로 설정되어 있어야 합니다.

        // 3. 각 탭에 클릭 리스너를 설정하여 인디케이터를 토글합니다.
        tabs.forEachIndexed { index, tabView ->
            tabView.setOnClickListener {
                activateTab(index)
            }
        }
    }

    /**
     * 특정 인덱스의 탭을 활성화하고 나머지 탭과 인디케이터를 비활성화합니다.
     */
    private fun activateTab(activeIndex: Int) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = index == activeIndex

            // 탭 텍스트 스타일 업데이트 (선택된 탭만 볼드체)
            tab.setTypeface(null, if (isSelected) Typeface.BOLD else Typeface.NORMAL)

            // 인디케이터 가시성 업데이트 (해당 인디케이터만 표시)
            indicators[index].visibility = if (isSelected) View.VISIBLE else View.INVISIBLE

            // TODO: 여기에 ViewPager나 컨텐츠를 전환하는 로직을 추가합니다.
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 메모리 누수 방지를 위해 바인딩 객체를 null로 설정합니다.
        _binding = null
    }
}