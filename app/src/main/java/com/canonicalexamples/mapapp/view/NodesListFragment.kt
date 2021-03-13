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
import com.canonicalexamples.mapapp.databinding.FragmentNodesListBinding
import com.canonicalexamples.mapapp.util.observeEvent
import com.canonicalexamples.mapapp.viewmodels.NodesListViewModel
import com.canonicalexamples.mapapp.viewmodels.NodesListViewModelFactory

class NodesListFragment : Fragment() {

    private lateinit var binding: FragmentNodesListBinding
    private val viewModel: NodesListViewModel by viewModels {
        val app = activity?.application as MapApp
        NodesListViewModelFactory(app.database, app.webservice)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNodesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = NodesListAdapter(viewModel = viewModel)
        binding.fab.setOnClickListener {
            viewModel.addButtonClicked()
        }

        //
//        print("COORDINADAS: ")
//        var p = viewModel.getXYTile(40.333,-3.7675,19)

        //print(p.first)

        viewModel.navigate.observeEvent(viewLifecycleOwner) { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }

        viewModel.open_node.observeEvent(viewLifecycleOwner) { open_node ->
            if(open_node){
                val action = NodesListFragmentDirections.actionFirstFragmentToNodeFragment().setItemSelected(viewModel.itemSelected)
                findNavController().navigate(action)
            }

        }
    }
}
