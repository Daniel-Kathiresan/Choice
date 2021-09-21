package com.example.choice

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
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_match, container, false)

        flingAdapterView = view?.findViewById(R.id.swipe)

        data = ArrayList()

        //add data to array
        getUserData()

        arrayAdapter = ArrayAdapter(this.requireContext(), R.layout.item, R.id.data,
            data as ArrayList<String>
        )
        flingAdapterView?.adapter = arrayAdapter
        flingAdapterView?.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                (data as ArrayList<String>).removeAt(0)
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onLeftCardExit(o: Any) {
                Toast.makeText(context, "dislike", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(o: Any) {
                Toast.makeText(context, "like", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(i: Int) {}
            override fun onScroll(v: Float) {}
        })
        flingAdapterView?.setOnItemClickListener { i, _ ->
            Toast.makeText(
                context,
                "data is " + (data as ArrayList<String>)[i],
                Toast.LENGTH_SHORT
            ).show()


        }
        return view
    }

    //Writing function to get users data
    private fun getUserData(){
        var eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val name = ds.child("first name").getValue(String::class.java)
                    Log.d("TAG", name!!)
                    //Warning! Will be error if there is unformatted data
                    data?.add(name)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        usersdRef.addListenerForSingleValueEvent(eventListener)
    }
}