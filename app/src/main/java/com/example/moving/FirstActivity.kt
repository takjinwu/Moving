// src/main/java/com/example/moving/FirstActivity.kt
package com.example.moving

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // NavHostFragment가 포함된 activity_login.xml을 로드하여 nav_graph를 실행합니다.
        setContentView(R.layout.activity_login)
    }
}