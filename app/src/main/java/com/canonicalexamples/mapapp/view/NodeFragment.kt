package com.canonicalexamples.mapapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.mapapp.R
import com.canonicalexamples.mapapp.app.MapApp
import com.canonicalexamples.mapapp.databinding.FragmentNodeBinding
import com.canonicalexamples.mapapp.viewmodels.MapViewModel
import com.canonicalexamples.mapapp.viewmodels.TeasListViewModelFactory

class NodeFragment: Fragment(){

    private lateinit var binding: FragmentNodeBinding
    private val viewModel: MapViewModel by viewModels {
        val app = activity?.application as MapApp
        TeasListViewModelFactory(app.database, app.webservice)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNodeBinding.inflate(inflater, container, false)
        println("Hola soy Node fragment")
        //binding = FragmentNodeBinding.bind()
        //return inflater.inflate(R.layout.fragment_node, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val viewModel: MapViewModel by viewModels {
//            val app = activity?.application as MapApp
//            TeasListViewModelFactory(app.database, app.webservice)
//        }
        println(viewModel.numberOfItems)
        println(viewModel.itemSelected)
        //binding.titleNode.setText(viewModel.getSelectedNode().tag)
        println("Node opened:")
        binding.buttonBack.setOnClickListener{
            findNavController().navigate(R.id.action_nodeFragment_to_FirstFragment)
        }

    }

}