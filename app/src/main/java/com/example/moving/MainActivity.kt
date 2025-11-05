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
import android.widget.TextView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration // 메모리 누수 방지를 위해 import
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataTextView: TextView // 뷰 참조를 클래스 변수로 이동
    private var firestoreListener: ListenerRegistration? = null // 리스너 관리를 위한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // FAB 클릭 리스너 설정
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // Navigation Drawer 설정
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        // R.id.nav_host_fragment_content_main는 Navigation Component의 FragmentContainer ID입니다.
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // 상위 수준 대상을 설정하여 햄버거 메뉴 아이콘이 표시되도록 합니다.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // 화면에 데이터를 표시할 TextView 참조를 가져옵니다.
        dataTextView = findViewById<TextView>(R.id.firestore_data_textview)
        dataTextView.text = "데이터 로딩 중..."

        // Firestore 리스너 설정 함수 호출
        setupFirestoreListener()
    }

    /**
     * Firestore 데이터 실시간 감지 (addSnapshotListener) 로직 설정
     */
    private fun setupFirestoreListener() {
        val db = FirebaseFirestore.getInstance()

        firestoreListener = db.collection("test") // 컬렉션 이름: test
            .document("test")  // 문서 ID: test
            // addSnapshotListener()를 사용하여 실시간 감지 리스너 등록
            .addSnapshotListener { document: DocumentSnapshot?, e: FirebaseFirestoreException? ->

                // 1. 오류 처리
                if (e != null) {
                    dataTextView.text = "데이터 실시간 감지 실패: ${e.message}"
                    Log.e("FirestoreTest", "실시간 감지 실패", e)
                    // 리스너를 해제할 필요는 없습니다. 어차피 오류 발생 시 자동으로 멈출 수 있습니다.
                    return@addSnapshotListener
                }

                // 2. 문서 존재 여부 및 데이터 처리
                if (document != null && document.exists()) {
                    val testValue = document.getString("test")

                    if (testValue != null) {
                        dataTextView.text = "Firestore Data (Realtime): $testValue"
                        Log.d("FirestoreUI", "실시간 화면 업데이트 성공: $testValue")
                    } else {
                        dataTextView.text = "Firestore Error: 'test' 필드 값이 없거나 타입 오류"
                        Log.w("FirestoreTest", "필드 'test'의 값이 null이거나 없음")
                    }
                } else {
                    dataTextView.text = "Firestore Error: 문서를 찾을 수 없음"
                    Log.w("FirestoreTest", "문서 'test/test'를 찾을 수 없음")
                }
            }
    }

    /**
     * 메모리 누수 방지를 위해 Activity가 화면에서 사라질 때 리스너를 해제합니다.
     */
    override fun onStop() {
        super.onStop()
        firestoreListener?.remove()
        Log.d("FirestoreTest", "Firestore 리스너 해제됨 (onStop)")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // 메뉴 레이아웃을 인플레이트합니다. (R.menu.main)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        // Navigation Up (뒤로가기 또는 햄버거 메뉴)을 처리합니다.
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}