package com.example.choice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.choice.adapter.UserAdapter
import com.example.choice.firebase.FirebaseService
import com.example.choice.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_friend.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_friend.*


class FriendFragmentTemp : Fragment() {
    var userList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_temp, container, false)
    }


}