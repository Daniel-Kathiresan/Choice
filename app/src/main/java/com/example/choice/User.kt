package com.example.choice

import android.media.Image

class User {

    var first_name:String? = ""
    var last_name:String? = ""
    var email:String? = ""
    var bio: String? = ""
    var uid: String? = ""
    var image: Image? = null
    var like: Boolean = false
    var disLike: Boolean = false
    var match: Boolean = false

    constructor(){

    }

    constructor(
        fname: String?,lname: String?,email: String?,bio: String?, uid: String?, image: Image?, like: Boolean?, disLike: Boolean?, match: Boolean?
    ){
        this.first_name = first_name
        this.last_name = last_name
        this.email = email
        this.bio = bio
        this.uid = uid
        this.image = image
        this.like = false
        this.disLike = false
        this.match = false

    }
}