package com.example.choice

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.choice.FragmentNavigation
import com.example.choice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*



class FeedbackFragment : Fragment() {

    private lateinit var fAuth: FirebaseAuth
    private var firebaseUserID : String = ""
    private lateinit var database: DatabaseReference
    private lateinit var FeedbackText : EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_feedback, container, false)

        fAuth = Firebase.auth
        firebaseUserID = fAuth.currentUser!!.uid
        FeedbackText = view.findViewById(R.id.feedback_edit_text)
        database = FirebaseDatabase.getInstance().getReference("Users")

        if(firebaseUserID != null){
            database.child(firebaseUserID).get().addOnSuccessListener {
                if (it.exists()){
                    val feedback = it.child("feedback").value
                    FeedbackText.setText((feedback.toString()))
                }else{
                }
            }
        }else{
        }

        view.findViewById<ImageView>(R.id.feedback_Back).setOnClickListener {
            Log.d("Feedback Fragment", "Go to Setting Fragment")

            var navLogout = activity as FragmentNavigation
            navLogout.navigateFrag(LogoutFragment(), false)
        }

        view.findViewById<Button>(R.id.feedback_upload_button).setOnClickListener {
            Log.d("Feedback Fragment", "Update feedback")

            val settingfeedbackupdate = FeedbackText.text.toString()
            database.child(firebaseUserID).child("feedback").setValue(settingfeedbackupdate).addOnSuccessListener {
                Toast.makeText(context,"Thank you for your feedback", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}