package com.example.choice.recycleview

import android.view.Gravity
import android.widget.FrameLayout
import com.example.choice.R
import com.example.choice.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_image_message.*
import kotlinx.android.synthetic.main.item_image_message.message_root
import kotlinx.android.synthetic.main.item_image_message.textView_message_time
import kotlinx.android.synthetic.main.item_text_message.*
import java.text.SimpleDateFormat

abstract class MessageItem(private val message: Message) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        setTimeText(viewHolder)
        setMessageRootGravity(viewHolder)
    }

    private fun setTimeText(viewHolder: ViewHolder){
        val dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
        viewHolder.textView_message_time.text = dateFormat.format(message.time)
    }

    private fun setMessageRootGravity(viewHolder: ViewHolder){
        if(message.senderId == FirebaseAuth.getInstance().currentUser?.uid){
            viewHolder.message_root.apply{
                setBackgroundColor(R.drawable.rect_oval_white)
                val lParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.END)
                this.layoutParams = lParams
            }
        }else{
            viewHolder.message_root.apply {
                setBackgroundResource(R.drawable.rect_round_primary_color)
                val lParams = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.START)
                this.layoutParams = lParams
            }
        }
    }

}