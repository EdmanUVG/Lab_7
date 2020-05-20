package com.example.lab_7.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.lab_7.R
import com.example.lab_7.database.GuestDatabase
import com.example.lab_7.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private lateinit var viewModel: ResultViewModel

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_result,
            container,
            false
        )

        setHasOptionsMenu(true)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(this.activity).application

        // Create an instance of the View Model Factory
        val dataSource = GuestDatabase.getInstance(application).guestDatabaseDao
        val viewModelFactory = ResultViewModelFactory(dataSource)


        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)

        // To use the View Model with dta binding, you have to explicitly
        // give the binding object a reference to it.
        binding.viewModel = viewModel

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        binding.buttonRestart.setOnClickListener{ viewModel.onPlayAgain() }
        binding.buttonSeeGuest.setOnClickListener { viewModel.onSeeGuests() }

        // Navigates back to game when button is pressed
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(R.id.action_result_fragment_to_register_fragment)
                viewModel.onPlayAgainComplete()
            }
        })

        // Display Toas with all users registration information
        viewModel.eventSeeGuests.observe(viewLifecycleOwner, Observer { seeGuests ->
            if (seeGuests) {
                Toast.makeText(activity, "Users registerd", Toast.LENGTH_SHORT).show()
                viewModel.onSeeGuestsComplete()
            }
        })

        viewModel.totalCount.observe(viewLifecycleOwner, Observer { totalCount ->
            binding.textResult.text = "Invitados: " + totalCount.toString()
        })

        viewModel.totalRegistered.observe(viewLifecycleOwner, Observer { total ->
            binding.textRegistered.text = "Registrado: " + total.toString()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.result_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            val intent = Intent()
            intent.action= Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Hey check out this great app:")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
        return super.onOptionsItemSelected(item)
    }

}

