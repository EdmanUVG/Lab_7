package com.example.lab_7.ui.guestdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.example.lab_7.R
import com.example.lab_7.database.GuestDatabase
import com.example.lab_7.databinding.FragmentGuestDetailBinding
import com.example.lab_7.ui.rolesdetail.RolesDetailFragmentArgs
import com.example.lab_7.ui.rolesdetail.RolesDetailViewModel
import com.example.lab_7.ui.rolesdetail.RolesDetailViewModelFactory


class GuestDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GuestDetailFragment()
    }

    private lateinit var viewModel: GuestDetailViewModel

    private lateinit var binding: FragmentGuestDetailBinding

    private lateinit var viewModelFactory: GuestDetailViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_guest_detail,
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
        val dataSource = GuestDatabase.getInstance(application).guestDatabaseDao

        val guestViewFragmentArgs by navArgs<GuestDetailFragmentArgs>()

        viewModelFactory = GuestDetailViewModelFactory(dataSource, guestViewFragmentArgs.guestId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GuestDetailViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.guest.observe(viewLifecycleOwner, Observer {
            (activity as AppCompatActivity).supportActionBar?.title = viewModel.guest.value?.name
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            viewModel.deleteGuest()
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
