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
class RegisterFragment : Fragment() {
    //Init variables
    private lateinit var email: EditText
    private lateinit var fname : EditText
    private lateinit var lname : EditText
    private lateinit var password: EditText
    private lateinit var cnfrmPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register, container, false)
        //Variables for user info
        email = view.findViewById(R.id.reg_email)
        fname = view.findViewById(R.id.reg_fname)
        lname = view.findViewById(R.id.reg_lname)
        password = view.findViewById(R.id.reg_password)
        cnfrmPassword = view.findViewById(R.id.reg_cnfrm_password)
        //TODO: Add gender selection spinner (male, female, other), possibly add what gender you are seeking (looking for: male, female, both)

        view.findViewById<Button>(R.id.return_login_btn).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(LoginFragment(), false)
        }
        //password validation and error checking
        view.findViewById<Button>(R.id.create_account_btn).setOnClickListener {
            //Run validation function when called
            validateEmptyForm()

        }

        return view

    }

    private fun validateEmptyForm(){
        //Show warning icon (currently placeholder) when a criteria is met
        val icon = AppCompatResources.getDrawable(requireContext(),//icon from resources
        R.drawable.warningph)

        icon?.setBounds(0, 0,icon.intrinsicWidth,icon.intrinsicHeight)
        //check string user has entered is not empty
        when{
            TextUtils.isEmpty((email.text.toString().trim()))->{

                email.setError("Please Enter an Email Address",icon)
            }
            TextUtils.isEmpty((fname.text.toString().trim()))->{

                email.setError("Please Enter your First Name",icon)
            }
            TextUtils.isEmpty((lname.text.toString().trim()))->{

                email.setError("Please Enter your Last Name(s)",icon)
            }
            TextUtils.isEmpty((password.text.toString().trim()))->{

                password.setError("Please Enter Password",icon)
            }
            TextUtils.isEmpty((cnfrmPassword.text.toString().trim()))->{

                cnfrmPassword.setError("Please Confirm Password",icon)
            }
            //check if field are empty
            email.text.toString().isNotEmpty() &&
                    fname.text.toString().isNotEmpty() &&
                    lname.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty()&&
                    cnfrmPassword.text.toString().isNotEmpty()
                        ->
            {//Check if email is valid format
                if (email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    //Correct if matches format, then check password
                        if (fname.text.toString().matches(Regex("[a-zA-Z]+"))){
                            if (lname.text.toString().matches(Regex("[a-zA-Z]+"))){
                                if(password.text.toString().length>=5){
                                    //If more than 5 characters check confirm password
                                    if(password.text.toString() == cnfrmPassword.text.toString()){

                                        Toast.makeText(context,"Account Creation Successful",Toast.LENGTH_SHORT).show()
                                    }
                                    else{
                                        cnfrmPassword.setError("Password Does Not Match",icon)
                                    }
                                }else{
                                    password.setError("Please enter at least 5 characters",icon)
                                }
                            }else{
                                lname.setError("Name can only contain letters",icon)
                            }
                        }else{
                            fname.setError("Name can only contain letters", icon)
                        }
                }else {
                    email.setError("Please Enter Valid Email Address", icon)
                }
            }//Checks if all fields are valid for registration. Email: Base on format, last and first name: letters only, password: more than 5 chars, confirm password: must match password
        }
    }
}