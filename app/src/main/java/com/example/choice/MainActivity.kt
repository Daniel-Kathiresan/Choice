package com.example.choice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_bottom_navigation_bar.*

//Following code may be moved

class MainActivity : AppCompatActivity(), FragmentNavigation{

    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fAuth = Firebase.auth

        //Enables automatic login to app, does not use a checkbox (remember me)
        val currentUser = fAuth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MatchActivity::class.java)
            startActivity(intent)
//            supportFragmentManager.beginTransaction()
//                .add(R.id.container, MatchFragment()).addToBackStack(null)
//                .commit()

        }else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LoginFragment())
                .commit()
        }
    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)

        if(addToStack){
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

}