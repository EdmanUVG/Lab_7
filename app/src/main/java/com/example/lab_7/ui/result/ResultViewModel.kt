package com.example.lab_7.ui.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab_7.database.GuestDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class ResultViewModel(val database: GuestDatabaseDao) : ViewModel() {

    var totalCount = database.getGuestCount()

    var totalRegistered = database.getGuestsRegistered()

    // viewModelJob allows us to cancel all coroutines started by the ViewModel
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Event for play again button
    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    // Event for see guests button
    private val _eventSeeGuests = MutableLiveData<Boolean>()
    val eventSeeGuests: LiveData<Boolean>
        get() = _eventSeeGuests


    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

    fun onSeeGuests() {
        _eventSeeGuests.value = true
    }

    fun onSeeGuestsComplete() {
        _eventSeeGuests.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ResultViewModel", "GameviewModel destroyed")
    }
}
