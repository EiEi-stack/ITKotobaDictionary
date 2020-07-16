package com.example.itkotobadictionary

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_search -> {
                this.startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            R.id.nav_recently_search -> {
                Toast.makeText(this, "Recently Searched clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_favourite -> {
                Toast.makeText(this, "Favourite clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_learning -> {
                Toast.makeText(this, "Learning clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_setting_language-> {
                Toast.makeText(this, "Language clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_setting_theme-> {
                Toast.makeText(this, "Theme clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
