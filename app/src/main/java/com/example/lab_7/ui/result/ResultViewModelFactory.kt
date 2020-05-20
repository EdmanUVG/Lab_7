package com.example.lab_7.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab_7.database.GuestDatabaseDao

class ResultViewModelFactory(private val database: GuestDatabaseDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class") as Throwable
    }
}