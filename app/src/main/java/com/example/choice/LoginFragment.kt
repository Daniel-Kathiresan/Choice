package com.example.choice

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var email: EditText
    private lateinit var password: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        email = view.findViewById(R.id.login_email)
        password = view.findViewById(R.id.login_password)

        view.findViewById<Button>(R.id.registerButton).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(RegisterFragment(), false)
        }

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            //Run validation function when called
            validateForm()

        }

        return view
    }


    private fun validateForm(){
        //Show warning icon (currently placeholder) when a criteria is met
        val icon = AppCompatResources.getDrawable(requireContext(),
            R.drawable.warningph)

        icon?.setBounds(0, 0,icon.intrinsicWidth,icon.intrinsicHeight)
        //check string user has entered
        when{
            TextUtils.isEmpty((email.text.toString().trim()))->{

                email.setError("Please Enter an Email Address",icon)
            }
            TextUtils.isEmpty((password.text.toString().trim()))->{

                password.setError("Please Enter Password",icon)
            }

            email.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() ->
            {//Currently checking if username is equal to email,will change to email later
                if (email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    //Correct if matches format, then check password
                    if(password.text.toString().length>=5){
                        //If more than 5 characters check confirm password
                        Toast.makeText(context,"Login successful", Toast.LENGTH_SHORT).show()
                    }else{
                        password.setError("Please enter at least 5 characters",icon)
                    }
                }else{
                    email.setError("Please Enter Valid Email Id",icon)
                }

            }
        }
    }
}