package com.example.twitterclone.utils

data class User(
    val email: String? = "",
    val username: String? = "",
    val imageUrl: String? = "",
    val followHashtags: ArrayList<String>? = arrayListOf(),
    val followUsers: ArrayList<String>? = arrayListOf()
)