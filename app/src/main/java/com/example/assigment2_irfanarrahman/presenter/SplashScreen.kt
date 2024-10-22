package com.example.assigment2_irfanarrahman.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.assigment2_irfanarrahman.databinding.ActivitySplashScreenBinding
import com.example.assigment2_irfanarrahman.data.source.local.preference.PreferenceDataStore
import com.example.assigment2_irfanarrahman.data.source.local.preference.dataStore

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val pref by lazy {
        PreferenceDataStore.getInstance(application.dataStore)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            if (pref.getLogin() == true){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        },3000)

    }
}