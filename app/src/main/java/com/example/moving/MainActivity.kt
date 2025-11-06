package com.example.moving

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem // MenuItem import
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
import androidx.core.view.GravityCompat // GravityCompat import
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataTextView: TextView
    private var firestoreListener: ListenerRegistration? = null

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

        // ⭐ 이 코드를 제거/주석 처리하여 자동 햄버거 메뉴 생성을 막습니다.
        // setupActionBarWithNavController(navController, appBarConfiguration)

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

        firestoreListener = db.collection("test")
            .document("test")
            .addSnapshotListener { document: DocumentSnapshot?, e: FirebaseFirestoreException? ->

                // 1. 오류 처리
                if (e != null) {
                    dataTextView.text = "데이터 실시간 감지 실패: ${e.message}"
                    Log.e("FirestoreTest", "실시간 감지 실패", e)
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
        // ⭐ R.menu.main 대신 오른쪽 버튼을 위한 메뉴를 인플레이트합니다. (right_menu가 존재해야 함)
        menuInflater.inflate(R.menu.right_menu, menu)
        return true
    }

    // ⭐ 메뉴 항목 클릭 시 드로어를 열도록 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 오른쪽 툴바의 버튼(ID: action_open_drawer)이 클릭되었는지 확인
        if (item.itemId == R.id.action_open_drawer) {
            val drawerLayout: DrawerLayout = binding.drawerLayout
            // 드로어를 오른쪽 (end)에서 엽니다. (activity_main.xml에서 layout_gravity="end"로 변경했기 때문)
            drawerLayout.openDrawer(GravityCompat.END)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        // 뒤로 가기 버튼 처리만 남깁니다. (햄버거 메뉴 처리는 onOptionsItemSelected에서 수동 처리)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}