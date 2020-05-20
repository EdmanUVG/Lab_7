package com.example.lab_7.ui.roles

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.lab_7.R

@BindingAdapter("typeImage")
fun ImageView.setTypeImage(index: Int?) {
    index?.let {
        setImageResource(when (index) {
            1 -> R.drawable.ic_photo_camera
            2 -> R.drawable.ic_power
            3 -> R.drawable.ic_room_service
            else -> R.drawable.logo
        })
    }
}