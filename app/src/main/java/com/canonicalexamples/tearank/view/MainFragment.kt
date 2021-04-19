package com.canonicalexamples.tearank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.app.MapApp
import com.canonicalexamples.tearank.databinding.FragmentMainBinding
import com.canonicalexamples.tearank.util.observeEvent
import com.canonicalexamples.tearank.viewmodels.MainViewModel
import com.canonicalexamples.tearank.viewmodels.MainViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
        val app = activity?.application as MapApp
        MainViewModelFactory(app.database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)



        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button1.setOnClickListener {
            viewModel.button1Clicked()
        }

        binding.button2.setOnClickListener {
            viewModel.button2Clicked()
        }

        binding.button3.setOnClickListener {
            viewModel.button3Clicked()
        }

        viewModel.go_to_history_fragment.observeEvent(viewLifecycleOwner) {
            if(it) {
                findNavController().navigate(R.id.action_MainFragment_to_HistoryFragment)
            }
        }

        viewModel.go_to_node_fragment.observeEvent(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_MainFragment_to_nodeFragment)
            }
        }
    }
}
