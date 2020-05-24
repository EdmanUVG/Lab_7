package com.example.lab_7.database

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(tableName="guest_table",
    foreignKeys = [
        ForeignKey(entity = GuestRole::class,
            parentColumns = ["id"],
            childColumns = ["role_id"],
            onDelete = SET_NULL
        )
    ])
data class Guest (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var guestId: Long = 0L,

    var name:String?,

    var phone: String?,

    var email : String?,

    var registered : Int,

    @Nullable
    var role_id: Long = 0L

)