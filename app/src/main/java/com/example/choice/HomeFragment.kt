package com.example.choice

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_home.*
import org.w3c.dom.Text

class HomeFragment : Fragment() {

    private lateinit var fAuth: FirebaseAuth
    private var firebaseUserID : String = ""
    private lateinit var database: DatabaseReference

  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        //Adds logout functionality to app
        fAuth = Firebase.auth
        firebaseUserID = fAuth.currentUser!!.uid
      view.findViewById<Button>(R.id.log_out_btn).setOnClickListener(){
            Firebase.auth.signOut()
            var navLogin = activity as FragmentNavigation
            navLogin.navigateFrag(LoginFragment(),addToStack = false)
        }

      if (firebaseUserID != null){
          database = FirebaseDatabase.getInstance().getReference("Users")
          database.child(firebaseUserID).get().addOnSuccessListener {
              if (it.exists()){
                  val fname = it.child("first name").value
                  val lname = it.child("last name").value
                  val gender = it.child("gender").value
                  view.findViewById<TextView>(R.id.fullnamefield).text = (fname.toString() + " " + lname.toString())
                  view.findViewById<TextView>(R.id.genderfield).text= (gender.toString())
              }else{
              }
          }
        }else {


      }



      return view
  }





}