package com.example.lab_7.ui.addroles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab_7.database.GuestRole
import com.example.lab_7.database.GuestRoleDatabaseDao
import kotlinx.coroutines.*


class AddRolesViewModel(val database: GuestRoleDatabaseDao) : ViewModel() {

    val role = MutableLiveData<String>()

    val desctiption = MutableLiveData<String>()

    val orden = MutableLiveData<Int>()

    val iconIndex = MutableLiveData<Int>()

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insertGuestRole(index: Int) {
        iconIndex.value = index
        uiScope.launch {
            insert()
        }
    }

    private suspend fun insert(){
        withContext(Dispatchers.IO) {
            database.insert(GuestRole(role = role.value ?: "", description = desctiption.value ?: "",
                order = orden.value?: 1, iconIndex = iconIndex.value ?: 0 ))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}