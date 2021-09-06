package com.example.choice

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ChatScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        findViewById<Button>(R.id.back_button).setOnClickListener{
            val intent = Intent(this, FriendList::class.java)
            startActivity(intent)
        }

        findViewById<FloatingActionButton>(R.id.menu_button).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == 0) && (resultCode == Activity.RESULT_OK) && (data != null)){
            Log.d("chatActivity", "selected photo sent")
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            //till the chat box complete
            uploadImageToFirebaseStorage()
        }
    }

    //upload the images to database(not complete)
    private fun uploadImageToFirebaseStorage(){
        var filename = UUID.randomUUID().toString()
        //var ref = Firebasestorage.getInstance().getReference("/images/$filename")

        //ref.putFile(selectedPhotoUri)
    }

}
