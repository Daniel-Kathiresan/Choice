package com.example.choice


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationBar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_bar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController =findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

    }
}