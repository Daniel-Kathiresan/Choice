package com.example.choice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_menu)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)


        //index to the matching interface(Not complete)
//        findViewById<Button>(R.id.matching_button).setOnClickListener{
//            val intent = Intent(this, MatchingActivity::class.java)
//            startActivity(intent)
//        }

        //index to the map interface
//        findViewById<Button>(R.id.map_button).setOnClickListener{
//            val intent = Intent(this, MapScreen::class.java)
//            startActivity(intent)
//        }

        //index to the chat interface
//        findViewById<Button>(R.id.bottomNav).setOnClickListener{
//            val intent = Intent(this, ChatScreen::class.java)
//            startActivity(intent)
//        }

        //index to the Me interface(Not complete)
//        findViewById<Button>(R.id.me_button).setOnClickListener{
//            val intent = Intent(this, MeActivity::class.java)
//            startActivity(intent)
//        }
    }
}