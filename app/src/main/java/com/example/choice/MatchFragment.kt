package com.example.choice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.*
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import java.util.ArrayList



class MatchFragment : Fragment() {

    private var arrayAdapter: ArrayAdapter<String>? = null
    var data: MutableList<String>? = null
    var flingAdapterView: SwipeFlingAdapterView? = null
    var rootRef = FirebaseDatabase.getInstance().reference
    var usersdRef = rootRef.child("Users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_match, container, false)

        return view
    }
}