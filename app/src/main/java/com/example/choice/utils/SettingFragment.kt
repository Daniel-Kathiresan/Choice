package com.example.choice.utils

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.choice.FragmentNavigation
import com.example.choice.LoginFragment
import com.example.choice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_setting.*
import java.util.*

private lateinit var fAuth: FirebaseAuth
    private var firebaseUserID : String = ""
    private lateinit var database: DatabaseReference
    private lateinit var biofield : EditText
    private lateinit var Userimage : ImageView
    private var selectedPhotoUri: Uri? = null

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_setting,container,false)

        fAuth = Firebase.auth
        firebaseUserID = fAuth.currentUser!!.uid
        biofield = view.findViewById(R.id.setting_bio_update)
        Userimage = view.findViewById(R.id.setting_user_head_image)
        database = FirebaseDatabase.getInstance().getReference("Users")

        if (firebaseUserID != null){
            database.child(firebaseUserID).get().addOnSuccessListener {
                if (it.exists()){
                    val fname = it.child("first name").value
                    val lname = it.child("last name").value
                    val gender = it.child("gender").value
                    val bio = it.child("bio").value
                    val profilepictre = it.child("profile picture").value
                    view.findViewById<TextView>(R.id.username_text_view).text = (fname.toString() + " " + lname.toString())
                    view.findViewById<TextView>(R.id.SettingGender).text= (gender.toString())
                    biofield.setText((bio.toString()))
                }else{
                }
            }
        }else {
        }

        view.findViewById<Button>(R.id.setting_updatebio_button).setOnClickListener(){
            Log.d("Setting Fragment", "Update Bio")

            val settingbioupdate = biofield.text.toString()
            database.child(firebaseUserID).child("bio").setValue(settingbioupdate).addOnSuccessListener {
                Toast.makeText(context,"Bio Update Complete", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.setting_button).setOnClickListener {

            Log.d("Setting Fragment", "Go to Logout Fragment")
            var navLogout = activity as FragmentNavigation
            navLogout.navigateFrag(LogoutFragment(), false)
        }

        view.findViewById<ImageView>(R.id.setting_user_head_image).setOnClickListener {
            Log.d("Setting Fragment", "Try to select photo")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }

        view.findViewById<Button>(R.id.setting_upload_photo).setOnClickListener {
            Log.d("Setting Fragment", "Upload photo")

            uploadPhotoToFirebase()
        }

        return view
    }

    private fun uploadPhotoToFirebase() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/Userimage/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Setting Fragment", "Successfully uploaded image: ${it.metadata?.path}")
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("Setting Activity", "Photo was selected")

            selectedPhotoUri = data.data

            Userimage.setImageURI(selectedPhotoUri)

        }
    }



}