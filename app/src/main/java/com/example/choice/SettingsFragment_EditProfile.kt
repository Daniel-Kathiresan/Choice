package com.example.choice

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_settings_fragment_edit_profile.*
import java.io.File
import java.net.URI
import java.util.*

class SettingsFragment_EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_fragment_edit_profile)


        edit_profile_done_text_view.setOnClickListener {
            val intent = Intent(this, SettingsScreen::class.java)
            startActivity(intent)
        }

        edit_profile_cancel_text_view.setOnClickListener {
            val intent = Intent(this, SettingsScreen::class.java)
            startActivity(intent)
        }

        user_head.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
            uploadImageToFirebaseStore()
        }
    }

    var userheadUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("Setting Activity", "Photo was selected")

            val userheadUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, userheadUri)

            val bitmapDrawable = BitmapDrawable(bitmap)
            user_head.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadImageToFirebaseStore(){
        if (userheadUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(userheadUri!!)
            .addOnSuccessListener {
                Log.d("Setting Activity", "Successfully upload image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Setting Activity", "File Location: $it")
                }
            }
    }
}