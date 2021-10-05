package com.example.choice.utils

import android.os.Bundle
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

    private lateinit var fAuth: FirebaseAuth
    private var firebaseUserID : String = ""
    private lateinit var database: DatabaseReference

class LogoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_logout, container, false)

        //Adds logout functionality to app
        fAuth = Firebase.auth
        firebaseUserID = fAuth.currentUser!!.uid
        database = FirebaseDatabase.getInstance().getReference("Users")

        view.findViewById<Button>(R.id.Logout_button).setOnClickListener(){
            Log.d("Logout Fragment", "Try to logout")

            Firebase.auth.signOut()
            var navLogout = activity as FragmentNavigation
            navLogout.navigateFrag(LogoutFragment(),addToStack = false)
        }

        view.findViewById<ImageView>(R.id.Goback_image_view).setOnClickListener {
            Log.d("Logout Fragment", "Go to Setting Fragment")

            var navSetting = activity as FragmentNavigation
            navSetting.navigateFrag(SettingFragment(), false)
        }
        return view
    }
}