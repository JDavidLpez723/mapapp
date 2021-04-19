package com.canonicalexamples.tearank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canonicalexamples.tearank.app.MapApp
import com.canonicalexamples.tearank.databinding.FragmentNodeBinding
import com.canonicalexamples.tearank.model.Node
import com.canonicalexamples.tearank.viewmodels.NodeViewModel
import com.canonicalexamples.tearank.viewmodels.NodeViewModelFactory

class NodeFragment: Fragment(){

    private lateinit var binding: FragmentNodeBinding

    //Recieve arguments (itemSelected)
//    val args: NodeFragmentArgs by navArgs()

    private var myNode: Node? = null

    private val viewModel: NodeViewModel by viewModels {
        val app = activity?.application as MapApp
        NodeViewModelFactory(app.database)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener { print(viewModel.prueba()) }

    }


}