package com.example.choice.model

data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}