package com.example.lab_7.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.lab_7.R
import com.example.lab_7.database.GuestDatabase
import com.example.lab_7.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var binding : FragmentRegisterBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val application = requireNotNull(this.activity).application

        // Create an instance of the View Model Factory
        val dataSource = GuestDatabase.getInstance(application).guestDatabaseDao
        val viewModelFactory = RegisterViewModelFactory(dataSource)

        // Get a reference to the ViewModel associated with this fragment
        registerViewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        // To use the View Model with dta binding, you have to explicitly
        // give the binding object a reference to it.
        binding.registerViewModel = registerViewModel

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner


        registerViewModel.registeredComplete.observe(viewLifecycleOwner, Observer {
            if (it) {
                requireView().findNavController().navigate(R.id.action_register_fragment_to_result_fragment)
                registerViewModel.finishRegister()
            }
        })


        registerViewModel.guests.observe(viewLifecycleOwner, Observer {
            registerViewModel.initialize(it)
            (activity as AppCompatActivity).supportActionBar?.title =
                getString(R.string.title_android_guest, registerViewModel.guestCount, registerViewModel.totalCount)
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.register_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_yes) {
            registerViewModel.updateCurrentGuest()
        }

        if(item.itemId == R.id.action_no) {
            registerViewModel.updateCurrentGuestNo()
        }

        return super.onOptionsItemSelected(item)
    }

}

