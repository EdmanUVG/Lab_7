package com.example.lab_7.ui.guestdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab_7.database.GuestDatabaseDao

class GuestDetailViewModelFactory(private val database: GuestDatabaseDao,
                                  private val guestId: Long): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuestDetailViewModel::class.java)) {
            return GuestDetailViewModel(database, guestId) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}