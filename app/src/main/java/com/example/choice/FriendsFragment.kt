package com.example.choice

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.choice.model.User
import com.example.choice.recycleview.UserItem
import com.example.choice.utils.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.messaging.FirebaseMessaging
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_friends.*


class FriendsFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var userListenerRegistration: ListenerRegistration

    private var shouldInitRecyclerView = true

    private lateinit var usersSection: Section


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view = inflater.inflate(R.layout.fragment_friends, container, false)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this.requireContext(),userList)

        userRecyclerView = view.findViewById(R.id.recyclerViewFriends)
        userRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        userRecyclerView.adapter = adapter

        mDbRef.child("Users").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    userList.add(currentUser!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        userListenerRegistration = FirestoreUtil.addUsersListener(this.requireActivity(), this::updateRecyclerView)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirestoreUtil.removeListener(userListenerRegistration)
        shouldInitRecyclerView = true
    }

    private fun updateRecyclerView(items: List<Item>){

        fun init(){
            recyclerViewFriends.apply{
                layoutManager = LinearLayoutManager(this@FriendsFragment.context)
                adapter = GroupAdapter<GroupieViewHolder>().apply{
                    usersSection = Section(items)
                    add(usersSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems(){

        }

        if(shouldInitRecyclerView){
            init()
        }else{
            updateItems()
        }
    }

    private val onItemClick = OnItemClickListener { item, view ->
        if (item is UserItem) {
//            startActivity<ChatActivity>{
//                AppConstants.USER_NAME to item.user.name,
//                AppConstants.USER_ID to item.userId
//            }
            val i = Intent()
            i.setClass(requireActivity().applicationContext, ChatActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
    }

}