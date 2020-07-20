package com.example.itkotobadictionary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var getDictionaries: MutableList<Dictionaries>
    lateinit var dictionaryArrayAdapter: ArrayAdapter<*>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale() //load locale
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

        //load data to list view
        val lvResult = findViewById<ListView>(R.id.lv_result)
        val dataAccess: DatabaseAccess = DatabaseAccess.getInstance(this)!!
        dataAccess.open()
        val dictionaries = dataAccess.getDictionaries
        val dicadapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dictionaries)
        lvResult.adapter = dicadapter
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
            R.id.nav_setting_language -> {
                //language setting
                val listItems = arrayOf("English", "日本語", "မြန်မာဘာသာ")
                val sBuilder = AlertDialog.Builder(this@MainActivity)
                sBuilder.setTitle(getString(R.string.chooselanguage))
                sBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->

                    if (which == 0) {
                        setLocate("en")
                        recreate()
                    } else if (which == 1) {
                        setLocate("ja")
                        recreate()
                    } else if (which == 2) {
                        setLocate("my")
                        recreate()
                    }
                    dialog.dismiss()
                }
                val mDialog = sBuilder.create()
                mDialog.show()
            }
            R.id.nav_setting_theme -> {
                Toast.makeText(this, "Theme clicked", Toast.LENGTH_SHORT).show()
            }
        }
//        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale) //set default locale
        val config = Configuration()
        config.locale = locale //set configuration
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        //store in share preferences
        val editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocale() {
        val sharePreferences = getSharedPreferences("Setting", Activity.MODE_PRIVATE)
        val language = sharePreferences.getString("My_Lang", "")
        if (language != null) {
            setLocate(language)
        }
    }


}
