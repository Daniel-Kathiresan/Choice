package com.example.choice

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//This is for navigation to match
class MatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val intent = Intent (activity, MatchActivity::class.java)
        activity?.startActivity(intent)
        return inflater.inflate(R.layout.fragment_match, container, false)
    }
}