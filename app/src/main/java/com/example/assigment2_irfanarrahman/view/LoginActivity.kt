package com.example.assigment2_irfanarrahman.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.assigment2_irfanarrahman.databinding.ActivityLoginBinding
import com.example.assigment2_irfanarrahman.preference.PreferenceDataStore
import com.example.assigment2_irfanarrahman.preference.dataStore
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val pref by lazy {
        PreferenceDataStore.getInstance(application.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (checkValidate()) {
                lifecycleScope.launch {
                    pref.puLogin(true)
                    pref.putEmail(binding.etEmail.text.toString())
                }
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }

    private fun checkValidate(): Boolean {
        binding.apply {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty()) {
                etEmail.error = "Harap Masukan Email"
                return false
            }
            if (password.isEmpty()) {
                etEmail.error = "Harap Masukan Password"
                return false
            }
            if (email != "irfan@gmail.com" && password != "irfan123") {
                Toast.makeText(this@LoginActivity, "email dan passowrd tidak sesuai ", Toast.LENGTH_SHORT).show()
                return false
            }
            return true

        }
    }
}