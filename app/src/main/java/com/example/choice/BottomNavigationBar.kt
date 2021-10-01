package com.example.choice


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.choice.R
import com.example.choice.utils.SettingFragment
import kotlinx.android.synthetic.main.activity_bottom_navigation_bar.*

class BottomNavigationBar : AppCompatActivity() {

    private val matchFragment = MatchFragment()
    private val settingFragment = SettingFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_bar)

        replaceFragment(settingFragment)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment -> replaceFragment(matchFragment)
                R.id.settingsScreen -> replaceFragment(settingFragment)
            }
            true
        }
    }

    private fun replaceFragment (fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_yo, fragment)
            transaction.commit()
        }
    }
}