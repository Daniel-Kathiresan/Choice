package com.example.choice

//Data class for data pulled from firebase

data class Spot(
    var id: Long? = counter++,
    var bio: String? = "",
    var first_name: String? = "",
    var gender: String? = "" ,
    var gender_pref: String? = "",
    var last_name: String? = "",
    var profile_picture: String? = "",
    var uid: String? = ""
) {
    companion object {
        private var counter = 0L
    }
}