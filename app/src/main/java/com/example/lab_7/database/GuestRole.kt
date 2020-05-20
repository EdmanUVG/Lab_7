package com.example.lab_7.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guest_role_table")
data class GuestRole(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var roleId: Long = 0L,

    @NonNull
    val  role: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "order")
    val order: Int,

    val iconIndex: Int

) {
    override fun toString(): String {
        return role + description
    }
}