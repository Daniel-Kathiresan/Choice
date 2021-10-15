package com.example.choice.model

data class User(val fname: String,
                val lname: String,
                val bio: String,
                val profilePicturePath: String?,
                val registrationTokens: MutableList<String>) {
    constructor():this("","","" ,null, mutableListOf())
}