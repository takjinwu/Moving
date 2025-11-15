package com.example.moving

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Intent // Intent를 사용하기 위해 추가

// LoginFragment는 R.layout.fragment_login을 사용합니다.

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {

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
        // fragment_login.xml 레이아웃을 로드합니다.
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. 회원가입 버튼 (ID: login_button2) 클릭 리스너 설정
        val registerButton: Button = view.findViewById(R.id.login_button2)
        registerButton.setOnClickListener {
            // nav_graph.xml에 정의된 액션 ID로 이동 요청
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // 2. 로그인 버튼 (ID: login_button) 클릭 리스너 설정 (메인 Activity로 이동)
        val loginButton: Button = view.findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            // MainActivity로 이동하기 위한 Intent 생성
            val intent = Intent(activity, MainActivity::class.java)

            // 로그인 후 이전 화면들(FirstActivity, LoginFragment)을 백 스택에서 모두 제거합니다.
            // 이렇게 하면 뒤로 가기 버튼을 눌러도 로그인 화면으로 돌아가지 않습니다.
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // MainActivity 시작
            startActivity(intent)

            // 현재 Activity (FirstActivity) 종료
            activity?.finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}