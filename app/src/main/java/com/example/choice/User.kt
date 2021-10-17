package com.example.choice

import android.media.Image

class User {

    var uid: String? = ""
    var first_name:String? = ""
    var last_name:String? = ""
    var bio: String? = ""
    var image: String? = null
//    var like: Boolean = false
//    var disLike: Boolean = false
//    var match: Boolean = false

    constructor(){

    }

    constructor(
        fname: String?,lname: String?,bio: String?, uid: String?, image: String?
    ){
        this.first_name = fname
        this.last_name = lname
        this.bio = bio
        this.uid = uid
        this.image = image
//        this.like = false
//        this.disLike = false
//        this.match = false

    }
}