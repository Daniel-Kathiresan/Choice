package com.example.choice.recycleview

import android.content.Context
import com.bumptech.glide.Glide
import com.example.choice.R
import com.example.choice.model.User
import com.example.choice.utils.StorageUtil
import kotlinx.android.synthetic.main.item_user.*


class UserItem(val user: User, val userId: String, private val context: Context) : com.xwray.groupie.kotlinandroidextensions.Item() {
    override fun bind(viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder, position: Int) {
        viewHolder.user_name.text = user.first_name + user.last_name
        viewHolder.user_bio.text = user.bio
        if(user.profilePicturePath != null)
            Glide.with(context)
                .load(StorageUtil.pathToReference(user.profilePicturePath))
                .placeholder(R.drawable.img)
                .into(viewHolder.user_image)

    }

    override fun getLayout() = R.layout.item_user
}