package com.example.moving

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // activity_login.xml 연결 (FragmentContainerView가 포함된 레이아웃)
        setContentView(R.layout.activity_login)

        // Activity가 처음 생성될 때만 LoginFragment를 띄웁니다.
        if (savedInstanceState == null) {
            // FragmentManager를 사용하여 R.id.fragment_container에 LoginFragment를 추가
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container, // Fragment를 띄울 컨테이너 뷰의 ID
                    LoginFragment()          // 띄울 Fragment 객체
                )
                .commit()
        }
    }
}