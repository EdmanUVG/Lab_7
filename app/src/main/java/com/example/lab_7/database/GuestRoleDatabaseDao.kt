package com.example.lab_7.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GuestRoleDatabaseDao {

    @Insert
    fun insert(guestRole: GuestRole)

    @Update
    fun update(guest: GuestRole)

    @Delete
    fun delete(guest: GuestRole)

    @Query("SELECT * FROM guest_role_table WHERE id = :key")
    fun getGuestRole(key:Long): LiveData<GuestRole>

    @Query("SELECT * FROM guest_role_table")
    fun getGuestRoles(): LiveData<List<GuestRole>>

    @Query("SELECT COUNT(*) FROM guest_role_table")
    fun getGuestRoleCount(): LiveData<Int>
}