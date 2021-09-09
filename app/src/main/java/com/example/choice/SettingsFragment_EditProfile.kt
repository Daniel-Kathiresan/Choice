package com.example.choice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.choice.databinding.ActivitySettingsFragmentEditProfileBinding
import kotlinx.android.synthetic.main.activity_settings_fragment_edit_profile.*

class SettingsFragment_EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsFragmentEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_fragment_edit_profile)
        binding = ActivitySettingsFragmentEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageView.setImageURI(it)
            }
        )


        edit_profile_done_text_view.setOnClickListener {
            val intent = Intent(this, SettingsScreen::class.java)
            startActivity(intent)
        }

        edit_profile_cancel_text_view.setOnClickListener {
            val intent = Intent(this, SettingsScreen::class.java)
            startActivity(intent)
        }

        edit_profile_changehead_button.setOnClickListener {
            getImage.launch("image/*")
        }
    }
}