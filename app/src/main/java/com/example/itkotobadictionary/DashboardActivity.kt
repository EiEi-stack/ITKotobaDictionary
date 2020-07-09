package com.example.itkotobadictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class DashboardActivity : AppCompatActivity() {
    private val SPLASH_SCREEN_TIME_OUT: Long = 3000 //3 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))

            finish()
        }, SPLASH_SCREEN_TIME_OUT)
    }
}
