package com.example.moving

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.moving.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ListenerRegistration
import java.lang.Exception
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var dataTextView: TextView
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dataTextView 초기화 (필요하다면 주석 해제)
        // dataTextView = binding.appBarMain.contentMain.dataTextView

        // ⭐ 툴바 설정
        setSupportActionBar(binding.appBarMain.toolbar)


        // 1. 툴바 제목(프래그먼트 레이블) 표시 비활성화
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // 2. Navigation Component가 확보하는 햄버거/뒤로가기 아이콘 공간 제거
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        binding.appBarMain.toolbar.navigationIcon = null


        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // AppBarConfiguration은 Navigation 동작을 위해 유지합니다.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_monthly_discount,
                R.id.nav_movie_review, R.id.nav_movie_chart, R.id.nav_cinema_list, R.id.nav_setting
            ), drawerLayout
        )



        // 드로어 메뉴 항목 선택 시 수동 처리 (오른쪽 드로어 사용)
        navView.setNavigationItemSelectedListener { item ->
            val handled = try {
                navController.navigate(item.itemId)
                true
            } catch (e: Exception) {
                Log.e("Navigation", "Navigation failed for item ${item.itemId}: ${e.message}")
                false
            }

            // 드로어를 명시적으로 닫습니다.
            drawerLayout.closeDrawer(GravityCompat.END)
            handled
        }

        // setupFirestoreListener() // Firestore 리스너 호출은 필요에 따라 주석 해제하세요.
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // ⭐ [수정] 햄버거 아이콘이 없으므로, Up 버튼 클릭 시 뒤로 가기만 처리합니다.
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // Firestore 관련 함수 (필요하다면 주석 해제)
    /*
    private fun setupFirestoreListener() {
        // ... (Firestore 리스너 설정 로직)
    }

    override fun onStop() {
        super.onStop()
        firestoreListener?.remove()
        Log.d("FirestoreTest", "Firestore 리스너 해제됨 (onStop)")
    }
    */

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // 오른쪽 버튼을 위한 메뉴를 인플레이트합니다. (R.menu.right_menu가 존재해야 함)
        menuInflater.inflate(R.menu.right_menu, menu)
        return true
    }

    // 메뉴 항목 클릭 시 드로어를 열도록 처리 (오른쪽 드로어 버튼)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 오른쪽 툴바의 버튼(ID: action_open_drawer)이 클릭되었는지 확인
        if (item.itemId == R.id.action_open_drawer) {
            val drawerLayout: DrawerLayout = binding.drawerLayout
            // 드로어를 오른쪽 (end)에서 엽니다.
            drawerLayout.openDrawer(GravityCompat.END)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}