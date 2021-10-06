package com.example.choice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.ContentView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_bottom_nav.*

class BottomNav : AppCompatActivity() {

    private var fragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.dashboardContainer, ChatsFragment()).commit()
            bottomChip.setItemSelected(R.id.btnChats)
        }

        bottomChip.setOnItemSelectedListener { id ->
            when (id){
                R.id.btnChats -> {
                    fragment = ChatsFragment()
                }
                R.id.btnFriends -> {
                    fragment = FriendsFragment()
                }
            }

            fragment!!.let {
                supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment!!).commit()
            }
        }
    }
}