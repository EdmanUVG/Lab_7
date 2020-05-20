package com.example.lab_7.ui.addroles

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.lab_7.R
import com.example.lab_7.database.GuestDatabase
import com.example.lab_7.databinding.FragmentAddRolesBinding


class AddRolesFragment : Fragment() {

    private lateinit var viewModelFactory: AddRolesViewModelFactory
    private lateinit var viewModel: AddRolesViewModel

    private lateinit var binding: FragmentAddRolesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_roles,
            container,
            false
        )

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = GuestDatabase.getInstance(application).guestRoleDatabaseDao
        viewModelFactory = AddRolesViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddRolesViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.orden.observe(viewLifecycleOwner, Observer { order ->
            binding.seekBarValue.text = order.toString()
        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            viewModel.insertGuestRole(binding.iconIndexText.text.toString().toInt())
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}

