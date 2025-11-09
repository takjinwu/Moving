package com.example.moving

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // NavHostFragment가 포함된 activity_login.xml을 로드합니다.
        setContentView(R.layout.activity_login)

        // Navigation Component가 이동을 처리하므로, 아래 코드는 필요 없습니다.
        /* if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    LoginFragment()
                )
                .commit()
        }
        */
    }
}