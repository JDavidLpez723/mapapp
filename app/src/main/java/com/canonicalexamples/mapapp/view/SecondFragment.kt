package com.canonicalexamples.mapapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.mapapp.R
import com.canonicalexamples.mapapp.app.MapApp
import com.canonicalexamples.mapapp.databinding.FragmentNodesListBinding
import com.canonicalexamples.mapapp.databinding.FragmentSecondBinding
import com.canonicalexamples.mapapp.util.observeEvent
import com.canonicalexamples.mapapp.viewmodels.NodesListViewModel
import com.canonicalexamples.mapapp.viewmodels.NodesListViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding;
    private val viewModel: NodesListViewModel by viewModels {
        val app = MapApp()
        NodesListViewModelFactory(app.database, app.webservice)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addPointToList.setOnClickListener {
            viewModel.addButtonClicked(2)
        }
        viewModel.navigate.observeEvent(viewLifecycleOwner) { navigate ->
            if (navigate) {
                viewModel.navigate_index.observeEvent(viewLifecycleOwner) { navigate_index ->
                    if (navigate_index == 2) {
                        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    }
                }
            }
        }

        println("[SecondFragment]: "+viewModel.numberOfItems)
       // view.findViewById<Button>(R.id.button_second).setOnClickListener {
       //     findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
       // }
    }
}
