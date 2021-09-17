package com.example.choice

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
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
    private lateinit var biofield : EditText

  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        //Adds logout functionality to app
        fAuth = Firebase.auth
        firebaseUserID = fAuth.currentUser!!.uid
        biofield = view.findViewById(R.id.bio_update)
        database = FirebaseDatabase.getInstance().getReference("Users")
      view.findViewById<Button>(R.id.log_out_btn).setOnClickListener(){
            Firebase.auth.signOut()
            var navLogin = activity as FragmentNavigation
            navLogin.navigateFrag(LoginFragment(),addToStack = false)
        }
      view.findViewById<Button>(R.id.updatebio).setOnClickListener(){
          //TODO: Move to settings screen
          val bioupdate = biofield.text.toString()
          database.child(firebaseUserID).child("bio").setValue(bioupdate).addOnSuccessListener {
              Toast.makeText(context,"Bio Update Complete",Toast.LENGTH_SHORT).show()

          }

      }

      if (firebaseUserID != null){

          database.child(firebaseUserID).get().addOnSuccessListener {
              if (it.exists()){
                  val fname = it.child("first name").value
                  val lname = it.child("last name").value
                  val gender = it.child("gender").value
                  val bio = it.child("bio").value
                  view.findViewById<TextView>(R.id.name).text = (fname.toString() + " " + lname.toString())
                  view.findViewById<TextView>(R.id.gender).text= (gender.toString())
                  biofield.setText((bio.toString()))
              }else{
              }
          }
        }else {


      }



      return view
  }





}