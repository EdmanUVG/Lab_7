package com.example.lab_7.database

import androidx.room.Embedded

data class GuestWithRole (
    @Embedded
    val guest: Guest,

    val role: String,

    val iconIndex: Int

)