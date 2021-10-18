package com.example.choice

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private lateinit var recycler_view_messages: RecyclerView
    private lateinit var editText_message: EditText
    private lateinit var imageView_send: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("first_name"+" "+"last_name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name

        recycler_view_messages = findViewById(R.id.recycler_view_messages)
        editText_message = findViewById(R.id.editText_message)
        imageView_send = findViewById(R.id.imageView_send)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)


        recycler_view_messages.layoutManager = LinearLayoutManager(this)
        recycler_view_messages.adapter = messageAdapter

        imgBack.setOnClickListener {
            onBackPressed()
        }

        displayUserName.setText(name)

        // logic for adding data to recyclerView
        mDbRef.child("Chat").child(senderRoom!!).child("messages").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()

                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        // Adding the message to database
        imageView_send.setOnClickListener{
            val message = editText_message.text.toString()
            val messageObject = Message(message, senderUid)

            mDbRef.child("Chat").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener{
                mDbRef.child("Chat").child(receiverRoom!!).child("messages").push().setValue(messageObject)
            }
            editText_message.setText("")
        }
    }

}