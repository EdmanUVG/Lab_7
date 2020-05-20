package com.example.lab_7.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab_7.database.GuestDatabaseDao
import com.example.lab_7.database.GuestRoleDatabaseDao
import java.lang.IllegalArgumentException

class AddViewModelFactory(private val database: GuestDatabaseDao,
                          private val databaseRole: GuestRoleDatabaseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(database, databaseRole) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}