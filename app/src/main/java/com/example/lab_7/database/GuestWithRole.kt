package com.example.lab_7.database

import androidx.annotation.Nullable
import androidx.room.Embedded

data class GuestWithRole (
    @Embedded
    val guest: Guest,

    val role: String?,

    @Nullable
    val iconIndex: Int

)