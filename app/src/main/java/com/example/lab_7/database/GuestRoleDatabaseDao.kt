package com.example.lab_7.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GuestRoleDatabaseDao {

    @Insert
    fun insert(guestRole: GuestRole)

    @Update
    fun update(guest: GuestRole)

    @Query("SELECT * FROM guest_role_table WHERE id = :key")
    fun getGuestRole(key:Long): GuestRole?

    @Query("SELECT * FROM guest_role_table")
    fun getGuestRoles(): LiveData<List<GuestRole>>

    @Query("SELECT COUNT(*) FROM guest_role_table")
    fun getGuestRoleCount(): LiveData<Int>
}