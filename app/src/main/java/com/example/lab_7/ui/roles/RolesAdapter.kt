package com.example.lab_7.ui.roles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_7.database.GuestRole
import com.example.lab_7.databinding.RolesItemLayoutBinding

class RolesAdapter(val listener: RolesClickListener) : ListAdapter<GuestRole, RolesAdapter.ViewHolder>(RolesDiffCallback()) {

    override fun onBindViewHolder(holder: RolesAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(listener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RolesItemLayoutBinding)
        :RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: RolesClickListener, item:GuestRole) {
            binding.guestRole = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RolesItemLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class RolesDiffCallback: DiffUtil.ItemCallback<GuestRole>() {
    override fun areItemsTheSame(oldItem: GuestRole, newItem: GuestRole): Boolean {
        return oldItem.roleId == newItem.roleId
    }

    override fun areContentsTheSame(oldItem: GuestRole, newItem: GuestRole): Boolean {
        return oldItem == newItem
    }
}

class RolesClickListener(val clickListener: (roleId: Long) -> Unit) {
    fun onClick(role: GuestRole) = clickListener(role.roleId)
}