package com.example.choice

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.choice.adapter.UserAdapter
import com.example.choice.firebase.FirebaseService
import com.example.choice.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_friend.*

class FriendList : AppCompatActivity() {
    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_friend)

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)


        userRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)



        imgBack.setOnClickListener {
            onBackPressed()
        }

//        imgProfile.setOnClickListener {
//            val intent = Intent(
//                this@FriendList,
//                ProfileActivity::class.java
//            )
//            startActivity(intent)
//        }

        getUsersList()

    }

    fun getUsersList() {
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

        var userid = firebase.uid
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/$userid")


        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
//                val currentUser = snapshot.getValue(User::class.java)
//                if (currentUser!!.profileImage == ""){
//                    imgProfile.setImageResource(R.drawable.profile_image)
//                }else{
//                    Glide.with(this@UserActivity).load(currentUser.profileImage).into(imgProfile)
//                }

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(User::class.java)

                    if (!user!!.userId.equals(firebase.uid)) {

                        userList.add(user)
                    }
                }

                val userAdapter = UserAdapter(this@FriendList, userList)

                userRecyclerView.adapter = userAdapter
            }

        })
    }
}