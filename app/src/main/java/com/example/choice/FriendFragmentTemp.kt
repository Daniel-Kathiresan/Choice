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

//        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//
//
//        userRecyclerView.layoutManager = LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false)
//
//
//        imgBack.setOnClickListener {
//            onBackPressed()
//        }


        return inflater.inflate(R.layout.fragment_friend_temp, container, false)

    }

//    fun getUsersList() {
//        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
//
//        var userid = firebase.uid
//        FirebaseMessaging.getInstance().subscribeToTopic("/topics/$userid")
//
//
//        val databaseReference: DatabaseReference =
//            FirebaseDatabase.getInstance().getReference("Users")
//
//
//        databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                userList.clear()
////                val currentUser = snapshot.getValue(User::class.java)
////                if (currentUser!!.profileImage == ""){
////                    imgProfile.setImageResource(R.drawable.profile_image)
////                }else{
////                    Glide.with(this@FriendList).load(currentUser.profileImage).into(imgProfile)
////                }
//
//                for (dataSnapShot: DataSnapshot in snapshot.children) {
//                    val user = dataSnapShot.getValue(User::class.java)
//
//                    if (!user!!.userId.equals(firebase.uid)) {
//
//                        userList.add(user)
//                    }
//                }
//
//                val userAdapter = getActivity()?.let { UserAdapter(it, userList) }
//
//                userRecyclerView.adapter = userAdapter
//            }
//
//        })
//    }

}