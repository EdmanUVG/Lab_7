package com.example.lab_7.ui.rolesdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab_7.database.GuestRoleDatabaseDao

class RolesDetailViewModelFactory(private val database: GuestRoleDatabaseDao,
private val roleId:Long): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RolesDetailViewModel::class.java)) {
            return RolesDetailViewModel(database, roleId) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}