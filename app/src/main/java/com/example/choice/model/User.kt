package com.example.choice.model

data class User(val first_name: String,
                val last_name: String,
                val bio: String,
                val uid: String,
                val profilePicturePath: String?,
                val registrationTokens: MutableList<String>) {
    constructor():this("","","" ,"",null, mutableListOf())
}