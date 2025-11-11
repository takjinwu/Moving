package com.example.moving

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.moving.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main) // NavController를 미리 가져옵니다.

        setSupportActionBar(binding.appBarMain.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        binding.appBarMain.toolbar.navigationIcon = null

        // ⭐️ 툴바 로고 클릭 리스너 설정
        // R.id.toolbar_logo는 app_bar_main.xml에 정의되어 있습니다.
        binding.appBarMain.toolbarLogo.setOnClickListener {
            try {
                // MainPageFragment의 ID인 nav_main_page로 이동합니다.
                navController.navigate(R.id.mainPageFragment)
            } catch (e: Exception) {
                Log.e("Navigation", "Error navigating to main page: ${e.message}")
            }
        }

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "FAB 클릭", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_monthly_discount, R.id.nav_movie_review,
                R.id.nav_movie_chart, R.id.nav_cinema_list, R.id.nav_setting, R.id.mainPageFragment // R.id.nav_main_page로 변경
            ), drawerLayout
        )

        navView.setNavigationItemSelectedListener { item ->
            val handled = try {
                navController.navigate(item.itemId)
                true
            } catch (e: Exception) {
                Log.e("Navigation", "Navigation failed for item ${item.itemId}: ${e.message}")
                false
            }

            drawerLayout.closeDrawer(GravityCompat.END)
            handled
        }

        val logoutButton = navView.findViewById<Button>(R.id.logout_button_footer)
        logoutButton.setOnClickListener {
            Snackbar.make(drawerLayout, "로그아웃 처리 중...", Snackbar.LENGTH_SHORT).show()

            val intent = Intent(this, FirstActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()

            drawerLayout.closeDrawer(GravityCompat.END)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.right_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_open_drawer) {
            binding.drawerLayout.openDrawer(GravityCompat.END)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}