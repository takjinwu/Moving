package com.example.moving

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController // NavController 사용을 위해 유지

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_register.xml 레이아웃을 로드합니다.
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    // ⭐ 핵심 수정 부분: 이동 로직 제거 및 다음 로직을 위한 준비 ⭐
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이 프래그먼트의 레이아웃 (fragment_register.xml)에는
        // ID가 'button'인 버튼이 있습니다.
        // 회원가입 완료 후 다음 화면으로 이동할 때 아래 코드를 사용하세요.
        /*
        val completeButton: Button = view.findViewById(R.id.button)
        completeButton.setOnClickListener {
            // nav_graph.xml에 action_registerFragment_to_homeFragment 정의 후 사용
            // findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }
        */
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}