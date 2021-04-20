package com.canonicalexamples.tearank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.app.MapApp
import com.canonicalexamples.tearank.databinding.FragmentHistoryBinding
import com.canonicalexamples.tearank.util.observeEvent
import com.canonicalexamples.tearank.viewmodels.HistoryViewModel
import com.canonicalexamples.tearank.viewmodels.HistoryViewModelFactory
import com.canonicalexamples.tearank.viewmodels.LoginViewModel
import com.canonicalexamples.tearank.viewmodels.LoginViewModelFactory

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModels {
        val app = activity?.application as MapApp
        HistoryViewModelFactory(app.database)
    }

    //Pruebas
    private val viewModel2: LoginViewModel by viewModels {
        val app = activity?.application as MapApp
        LoginViewModelFactory(app.database)
    }
    //

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = HistoryAdapter(viewModel = viewModel)

        viewModel.navigate.observeEvent(viewLifecycleOwner) { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }

        //Pruebas
        //view
        //
    }
}
