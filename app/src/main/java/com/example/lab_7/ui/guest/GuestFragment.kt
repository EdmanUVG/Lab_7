package com.example.lab_7.ui.guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.lab_7.R
import com.example.lab_7.database.GuestDatabase
import com.example.lab_7.databinding.FragmentGuestBinding
import kotlinx.android.synthetic.main.fragment_guest.*


class GuestFragment : Fragment() {

    private lateinit var viewModelFactory: GuestViewModelFactory
    private lateinit var viewModel: GuestViewModel
    private lateinit var binding: FragmentGuestBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_guest,
            container,
            false
        )


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        //Create an instance of the ViewModel Factory
        val dataSource = GuestDatabase.getInstance(application).guestDatabaseDao
        viewModelFactory = GuestViewModelFactory(dataSource)
        //Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProvider(this, viewModelFactory).get(GuestViewModel::class.java)


        // To use the View Model with dta binding, you have to explicitly
        // give the binding object a reference to it.
        binding.viewModel = viewModel

        viewModel.guestClicked.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                requireView().findNavController().navigate(GuestFragmentDirections
                    .actionGuestFragmentToGuestDetailFragment(it))
                viewModel.onGuestClickedCompleted()
            }
        })

        val adapter = GuestAdapter(GuestClickListener {
            viewModel.onGuestClicked(it)
        })

        binding.guestsList.adapter = adapter

        viewModel.guests.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_guest_fragment_to_add_fragment)
        }
    }
}

