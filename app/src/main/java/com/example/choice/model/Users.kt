package com.example.choice.model

class Users {
    var id: String? = null
    var username: String? = null
    var imageURL: String? = null

    constructor() {}
    constructor(id: String?, username: String?, imageURL: String?) {
        this.id = id
        this.username = username
        this.imageURL = imageURL
    }
}