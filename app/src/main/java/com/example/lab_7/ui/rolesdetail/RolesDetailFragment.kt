package com.example.lab_7.ui.rolesdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

import com.example.lab_7.R
import com.example.lab_7.database.GuestDatabase
import com.example.lab_7.database.GuestRole
import com.example.lab_7.databinding.FragmentRolesDetailBinding

class RolesDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RolesDetailFragment()
    }

    private lateinit var binding: FragmentRolesDetailBinding

    private lateinit var viewModel: RolesDetailViewModel

    private lateinit var viewModelFactory: RolesDetailViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_roles_detail,
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

        val guestRoleViewFragmentArgs by navArgs<RolesDetailFragmentArgs>()

        viewModelFactory = RolesDetailViewModelFactory(dataSource, guestRoleViewFragmentArgs.roleId)
        viewModel = ViewModelProviders.of(this).get(RolesDetailViewModel::class.java)

        binding.viewModel = viewModel

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            viewModel.deleteGuestRole()
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}

