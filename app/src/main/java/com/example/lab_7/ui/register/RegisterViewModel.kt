package com.example.lab_7.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab_7.database.Guest
import com.example.lab_7.database.GuestDatabaseDao
import com.example.lab_7.database.GuestWithRole
import kotlinx.coroutines.*

class RegisterViewModel(val database: GuestDatabaseDao) : ViewModel() {

    val guests = database.getGuestsWithRole()

    private val _registeredComplete = MutableLiveData<Boolean>()
    val registeredComplete: LiveData<Boolean>
        get() = _registeredComplete

    var guestCount = 1
        private set

    // Total of Guest in our database
    var totalCount = 0
        private set

    val currentGuest = MutableLiveData<GuestWithRole>()

    // viewModelJob allows us to cancel all coroutines started by the ViewModel
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun initialize(guests: List<GuestWithRole>) {
        totalCount = guests.size
        if (guests.isEmpty()) {
            _registeredComplete.value = true
        } else {
            currentGuest.value = guests[guestCount - 1]
        }
    }

    fun updateCurrentGuest() {
        val guestWithRole = currentGuest.value
        guestCount++
        // >=
        if (totalCount >= guestCount) {
            currentGuest.value = guests.value?.get(guestCount -1)
        }
        uiScope.launch {
            update(guestWithRole?.let {
                Guest(guestId = it.guest.guestId,
                    name = it.guest.name,
                    phone = it.guest.phone,
                    email = it.guest.email,
                    registered = true,
                    role_id = it.guest.role_id)
            })
            Log.i("@Edman", "True")
        }
        if(guestCount > totalCount) {
            _registeredComplete.value = true
        }
    }

    fun updateCurrentGuestNo() {
        val guestWithRole = currentGuest.value
        guestCount++
        if (totalCount >= guestCount) {
            currentGuest.value = guests.value?.get(guestCount -1)
        }
        uiScope.launch {
            update(guestWithRole?.let {
                Guest(guestId = it.guest.guestId,
                    name = it.guest.name,
                    phone = it.guest.phone,
                    email = it.guest.email,
                    registered = false,
                    role_id = it.guest.role_id)
            })
            Log.i("@Edman", "False")
        }
        if(guestCount > totalCount) {
            _registeredComplete.value = true
        }
    }

    private suspend fun update(guest: Guest?) {
        withContext(Dispatchers.IO) {
            guest ?.let {
                database.update(it)
            }
        }
    }

    fun finishRegister() {
        _registeredComplete.value = false
        guestCount = 1
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
