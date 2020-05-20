package com.example.lab_7.ui.guest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lab_7.database.GuestDatabaseDao
import com.example.lab_7.database.GuestWithRole
import java.lang.StringBuilder

class GuestViewModel(val database: GuestDatabaseDao) : ViewModel() {

    private val guests = database.getGuestsWithRole()

    val guestsText = Transformations.map(guests) {
        buildGuestsText(it)
    }

    private fun buildGuestsText(guestsWithRole: List<GuestWithRole>) : String {
        val guestsText = StringBuilder()
        for (qwt in guestsWithRole) {
            guestsText.append("Invitado: ${qwt.guest.guestId}\n" +
                    "Nombre: ${qwt.guest.name}\n" +
                    "Telefono: ${qwt.guest.phone}\n" +
                    "Correo: ${qwt.guest.email}\n" +
                    "Rol: ${qwt.role}\n\n")
        }
        return guestsText.toString()
    }

}