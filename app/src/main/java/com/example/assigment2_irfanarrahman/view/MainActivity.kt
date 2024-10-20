package com.example.assigment2_irfanarrahman.view


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.assigment2_irfanarrahman.R
import com.example.assigment2_irfanarrahman.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Diary App"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))

        replaceFragment(ListFragment())

        binding.bottomNav.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {

                    R.id.item_calendar ->{
                        replaceFragment(CalendarListFragment())
                        true
                    }
                    R.id.item_home -> {
                        replaceFragment(ListFragment())
                        true
                    }

                    R.id.item_profile -> {
                        replaceFragment(ProfileFragment())
                        true
                    }

                    else -> false
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }




}