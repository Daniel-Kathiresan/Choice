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
import com.example.choice.FragmentNavigation
import com.example.choice.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

private var firebaseUserID : String = ""
private lateinit var database: DatabaseReference
private lateinit var FeedbackText : EditText

class FeedbackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_feedback, container, false)

        FeedbackText = view.findViewById(R.id.feedback_edit_text)


        view.findViewById<ImageView>(R.id.feedback_Back).setOnClickListener {
            Log.d("Feedback Fragment", "Go to Setting Fragment")

            var navLogout = activity as FragmentNavigation
            navLogout.navigateFrag(LogoutFragment(), false)
        }

        view.findViewById<Button>(R.id.feedback_upload_button).setOnClickListener {

            uploadfbToFirebase()
        }

        return view
    }



    private fun uploadfbToFirebase() {
        if (FeedbackText == null) return


        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/UserFeedback/$filename")

    }
}