package com.example.lab_7.ui.guest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_7.database.Guest
import com.example.lab_7.database.GuestWithRole
import com.example.lab_7.databinding.GuestItemLayoutBinding

class GuestAdapter (val listener: GuestClickListener) : ListAdapter<GuestWithRole, GuestAdapter.ViewHolder>(GuestDiffCallback()) {

    override fun onBindViewHolder(holder: GuestAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(listener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestAdapter.ViewHolder {
        return GuestAdapter.ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: GuestItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: GuestClickListener, item:GuestWithRole) {
            binding.guest = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GuestItemLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}

class GuestDiffCallback: DiffUtil.ItemCallback<GuestWithRole>() {
    override fun areItemsTheSame(oldItem: GuestWithRole, newItem: GuestWithRole): Boolean {
        return oldItem.guest.guestId == newItem.guest.guestId
    }

    override fun areContentsTheSame(oldItem: GuestWithRole, newItem: GuestWithRole): Boolean {
        return oldItem == newItem
    }
}

class GuestClickListener(val clickListener: (guestId: Long) -> Unit) {
    fun onClick(guest: GuestWithRole) = clickListener(guest.guest.guestId)
}