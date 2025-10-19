package com.example.moving

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.moving.databinding.ActivityMainBinding
import android.util.Log
import android.widget.TextView // TextView 사용을 위해 필요
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // 화면에 데이터를 표시할 TextView 참조를 가져옵니다.
        // R.id.firestore_data_textview는 XML 레이아웃에 정의되어 있어야 합니다.
        val dataTextView = findViewById<TextView>(R.id.firestore_data_textview)
        dataTextView.text = "데이터 로딩 중..."
        // ===============================================
        // Firestore 데이터 실시간 감지 (addSnapshotListener) 로직
        val db = FirebaseFirestore.getInstance()

        db.collection("test") // 컬렉션 이름: test
            .document("test")  // 문서 ID: test
            // get() 대신 addSnapshotListener()를 사용하여 실시간 감지
            .addSnapshotListener { document: DocumentSnapshot?, e: FirebaseFirestoreException? ->

                // 1. 오류 처리
                if (e != null) {
                    dataTextView.text = "데이터 실시간 감지 실패: ${e.message}"
                    Log.e("FirestoreTest", "실시간 감지 실패", e)
                    return@addSnapshotListener
                }

                // 2. 문서 존재 여부 및 데이터 처리
                if (document != null && document.exists()) {
                    // Firestore 필드 'test'의 값을 가져옵니다.
                    val testValue = document.getString("test")

                    if (testValue != null) {
                        // 성공적으로 값을 가져오면 TextView를 업데이트합니다.
                        // Firestore 데이터가 변경될 때마다 이 부분이 실행됩니다.
                        dataTextView.text = "Firestore Data (Realtime): $testValue"
                        Log.d("FirestoreUI", "실시간 화면 업데이트 성공: $testValue")
                    } else {
                        // 필드는 있지만 값이 null이거나 형식이 다를 경우
                        dataTextView.text = "Firestore Error: 'test' 필드 값이 없거나 타입 오류"
                        Log.w("FirestoreTest", "필드 'test'의 값이 null이거나 없음")
                    }
                } else {
                    // 문서가 존재하지 않거나 삭제되었을 경우
                    dataTextView.text = "Firestore Error: 문서를 찾을 수 없음"
                    Log.w("FirestoreTest", "문서 'test/test'를 찾을 수 없음")
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}