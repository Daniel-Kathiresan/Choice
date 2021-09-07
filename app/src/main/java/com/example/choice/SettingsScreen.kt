package com.example.choice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings_screen.*
// import com.google.firebase.auth.ktx.actionCodeSettings

class SettingsScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_screen)

        setting_button.setOnClickListener {
            val intent = Intent(this, SettingsFragment::class.java)
            startActivity(intent)
        }

        edit_profile_button.setOnClickListener {
            val intent2 = Intent(this, SettingsFragment_EditProfile::class.java)
            startActivity(intent2)
        }


    }
}