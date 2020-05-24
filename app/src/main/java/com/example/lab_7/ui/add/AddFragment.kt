package com.example.lab_7.ui.add

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.lab_7.R
import com.example.lab_7.database.Guest
import com.example.lab_7.database.GuestDatabase
import com.example.lab_7.database.GuestRole
import com.example.lab_7.databinding.FragmentAddBinding
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class AddFragment : Fragment() {

    private lateinit var viewModelFactory: AddViewModelFactory
    private lateinit var viewModel: AddViewModel

    private lateinit var binding: FragmentAddBinding

    private var guest: Guest? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add,
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
        //Create an instance of the ViewModel Factory
        val dataSource = GuestDatabase.getInstance(application).guestDatabaseDao
        val dataSourceRole = GuestDatabase.getInstance(application).guestRoleDatabaseDao
        viewModelFactory = AddViewModelFactory(dataSource, dataSourceRole)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddViewModel::class.java)

        binding.viewModel = viewModel

        val items = arrayListOf<GuestRole>()
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        binding.spinner.adapter = arrayAdapter

        viewModel.rolesList.observe(viewLifecycleOwner, Observer {
            items.clear()
            items.addAll(it)
            if (it.isNotEmpty()) binding.spinner.setSelection(0)
            arrayAdapter.notifyDataSetChanged()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            val name = editText_name.text.toString().trim()
            val phone = editText_phone.text.toString().trim()
            val email = editText_email.text.toString().trim()

            if (name.isEmpty() && phone.isEmpty() && email.isEmpty()) {
                editText_name.error = "Nombre requerido"
                editText_name.requestFocus()
                editText_phone.error = "Telefono requerido"
                editText_phone.requestFocus()
                editText_email.error = "Correo requerido"
                editText_email.requestFocus()
            } else if (name.isEmpty()) {
                editText_name.error = "Nombre requerido"
                editText_name.requestFocus()
            } else if (phone.isEmpty()) {
                editText_phone.error = "Telefono requerido"
                editText_phone.requestFocus()
            } else if (email.isEmpty()) {
                editText_email.error = "Correo requerido"
                editText_email.requestFocus()
            } else {
                viewModel.insertGuest(binding.spinner.selectedItem)
                activity?.onBackPressed()
                Toast.makeText(activity, "¡Invitado guardado exitosamente!", Toast.LENGTH_SHORT).show()

                activity?.let { UIUtil.hideKeyboard(it) }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
