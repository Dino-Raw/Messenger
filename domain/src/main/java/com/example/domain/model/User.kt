package com.example.domain.model

data class User (
    var email: String = "",
    var name: String = email.substringBefore("@"),
    var bio: String = "",
    var timeStatus: String = "",
    var imageProfilePath: String = "",
)