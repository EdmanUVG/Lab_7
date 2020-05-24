package com.example.lab_7.ui.addroles

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.lab_7.R
import com.example.lab_7.database.GuestDatabase
import com.example.lab_7.databinding.FragmentAddRolesBinding
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add_roles.*
import kotlinx.android.synthetic.main.fragment_add_roles.editText_name
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


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

            val rol = editText_name.text.toString().trim()
            val description = editText_description.text.toString().trim()
            val index = icon_index_text.text.toString().trim()

            if (rol.isEmpty() && description.isEmpty() && index.isEmpty()) {
                editText_name.error = "Rol requerido"
                editText_name.requestFocus()
                editText_description.error = "Descripcion requerido"
                editText_description.requestFocus()
                icon_index_text.error = "Indice requerido"
                icon_index_text.requestFocus()
            } else if (rol.isEmpty()) {
                editText_name.error = "Rol requerido"
                editText_name.requestFocus()
            } else if (description.isEmpty()) {
                editText_description.error = "Descripcion requerido"
                editText_description.requestFocus()
            } else if (index.isEmpty()) {
                icon_index_text.error = "Indice requerido"
                icon_index_text.requestFocus()
            } else {
                viewModel.insertGuestRole(binding.iconIndexText.text.toString().toInt())
                activity?.onBackPressed()
                Toast.makeText(activity, "¡Rol guardado exitosamente!", Toast.LENGTH_SHORT).show()

                activity?.let { UIUtil.hideKeyboard(it) }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

