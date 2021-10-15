package com.example.choice

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.choice.model.User

class UserAdapter(val context: Context, val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.user_image)
        val textName = itemView.findViewById<TextView>(R.id.user_name)
        val textBio = itemView.findViewById<TextView>(R.id.user_bio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var currentUser = userList[position]
        holder.textName.text = currentUser.first_name+currentUser.last_name
        holder.textBio.text = currentUser.bio

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("first_name"+" "+"last_name", currentUser.first_name)
            intent.putExtra("uid", currentUser.uid)

            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}