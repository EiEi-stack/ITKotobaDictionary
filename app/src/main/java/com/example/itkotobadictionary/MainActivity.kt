package com.example.itkotobadictionary

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        loadLocale() //load locale
        loadTheme()// load theme
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) { // initial transaction should be wrapped like this
            var fragment: Fragment
            fragment = SearchFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.subMainContent, fragment)
                .commitAllowingStateLoss()
        }

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
        this.supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        this.supportActionBar?.hide()

    }

    override fun onStart() {
        super.onStart()
        this.supportActionBar?.hide()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment
        when (item.itemId) {
            R.id.nav_search -> {
                replaceFragmenty(
                    fragment = SearchFragment(),
                    allowStateLoss = true,
                    containerViewId = R.id.subMainContent
                )
                setTitle(resources.getString(R.string.search))
            }

            R.id.nav_recently_search -> {
                replaceFragmenty(
                    fragment = HistoryFragment(),
                    allowStateLoss = true,
                    containerViewId = R.id.subMainContent
                )
                title = resources.getString(R.string.recently_search)
            }
            R.id.nav_favourite -> {
                replaceFragmenty(
                    fragment = FavouriteFragment(),
                    allowStateLoss = true,
                    containerViewId = R.id.subMainContent
                )
                title = resources.getString(R.string.favourite)
            }
            R.id.nav_learning -> {

                replaceFragmenty(
                    fragment = LearningFragment(),
                    allowStateLoss = true,
                    containerViewId = R.id.subMainContent
                )
                title = resources.getString(R.string.learning)
            }
            R.id.nav_add_new_words -> {

                replaceFragmenty(
                    fragment = AddNewWords(),
                    allowStateLoss = true,
                    containerViewId = R.id.subMainContent
                )
                title = resources.getString(R.string.learning)
            }
            R.id.nav_report -> {
                replaceFragmenty(
                    fragment = ReportPloblem(),
                    allowStateLoss = true,
                    containerViewId = R.id.subMainContent
                )
                title = resources.getString(R.string.learning)
            }
            R.id.nav_setting_language -> {
                //language setting
                val listItems = arrayOf("English", "日本語")
//                val listItems = arrayOf("English", "日本語", "မြန်မာဘာသာ")
                val sBuilder = AlertDialog.Builder(this@MainActivity)
                sBuilder.setTitle(getString(R.string.choose_language))
                sBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->

                    if (which == 0) {
                        setLocate("en")
                        recreate()
                    } else if (which == 1) {
                        setLocate("ja")
                        recreate()
                    }
//                    else if (which == 2) {
//                        setLocate("my")
//                        recreate()
//                    }
                    dialog.dismiss()
                }
                val mDialog = sBuilder.create()
                mDialog.show()
                setTitle(resources.getString(R.string.language_setting))
            }
//            R.id.nav_setting_theme -> {
//                //theme setting
//                val listItems = arrayOf(
//                    getString(R.string.color_pink),
//                    getString(R.string.color_green),
//                    getString(R.string.color_red),
//                    getString(R.string.color_blue)
//                )
//                val sBuilder = AlertDialog.Builder(this@MainActivity)
//                sBuilder.setTitle(getString(R.string.chooseTheme))
//                sBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->
//
//                    if (which == 0) {
//                        setThemeColor("pink")
//                        recreate()
//                    } else if (which == 1) {
//                        setThemeColor("green")
//                        recreate()
//                    } else if (which == 2) {
//                        setThemeColor("red")
//                        recreate()
//                    } else if (which == 3) {
//                        setThemeColor("blue")
//                        recreate()
//                    }
//                    dialog.dismiss()
//                }
//                val mDialog = sBuilder.create()
//                mDialog.show()
//                setTitle(resources.getString(R.string.theme_setting))
//            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setThemeColor(theme: String) {
        if (theme == "green") {
            setTheme(R.style.green)
        } else if (theme == "blue") {
            setTheme(R.style.blue)
        } else if (theme == "pink") {
            setTheme(R.style.pink)
        } else if (theme == "red") {
            setTheme(R.style.red)
        }
        val editor = getSharedPreferences("Theme", Context.MODE_PRIVATE).edit()
        editor.putString("My_Theme", theme)
        editor.apply()
    }

    private fun loadTheme() {
        val sharePreferences = getSharedPreferences("Theme", Activity.MODE_PRIVATE)
        val theme = sharePreferences.getString("My_Theme", "")
        if (theme != null) {
            setThemeColor(theme)
        }
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
