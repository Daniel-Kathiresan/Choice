package com.example.choice

//Data class for the current users data pulled from firebase

data class CurrUser(
    var bio: String? = "",
    var first_name: String? = "",
    var gender: String? = "" ,
    var gender_pref: String? = "",
    var last_name: String? = "",
    var profile_picture: String? = "",
    var uid: String? = ""
)