package com.example.data.model

import com.example.domain.model.CurrentUser
import com.example.domain.model.User

fun CurrentUser.toUser(): User =
    User (
        name = this.name,
        bio = this.bio,
        timeStatus = this.timeStatus,
        imagePath = this.imagePath,
        id = this.id,
    )
